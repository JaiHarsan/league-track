// League Track Frontend JavaScript

document.addEventListener('DOMContentLoaded', () => {
    initTheme();
    initApp();
});

function initApp() {
    loadTeams();
    loadFixtures();
    loadStandings();
}

// ----------------------------------------------------
// Light / Dark Theme Management
// ----------------------------------------------------
function initTheme() {
    const savedTheme = localStorage.getItem('theme') || 'light';
    setTheme(savedTheme);
}

function toggleTheme() {
    const currentTheme = document.documentElement.getAttribute('data-theme') || 'light';
    const newTheme = currentTheme === 'dark' ? 'light' : 'dark';
    setTheme(newTheme);
}

function setTheme(theme) {
    document.documentElement.setAttribute('data-theme', theme);
    localStorage.setItem('theme', theme);

    const label = document.getElementById('theme-toggle-label');
    const sunIcon = document.querySelector('.sun-icon');
    const moonIcon = document.querySelector('.moon-icon');

    if (label) {
        label.textContent = theme === 'dark' ? 'Light' : 'Dark';
    }
    if (sunIcon && moonIcon) {
        if (theme === 'dark') {
            sunIcon.classList.remove('hidden');
            moonIcon.classList.add('hidden');
        } else {
            sunIcon.classList.add('hidden');
            moonIcon.classList.remove('hidden');
        }
    }
}

// ----------------------------------------------------
// Toast Alert Banner
// ----------------------------------------------------
function showAlert(message, type = 'success') {
    const alertBanner = document.getElementById('alert-banner');
    alertBanner.className = `alert-banner alert-${type}`;
    alertBanner.textContent = message;
    alertBanner.classList.remove('hidden');

    setTimeout(() => {
        alertBanner.classList.add('hidden');
    }, 4000);
}

// ----------------------------------------------------
// Dashboard Update
// ----------------------------------------------------
function updateDashboard(teamsCount, fixtures) {
    document.getElementById('stat-total-teams').textContent = teamsCount !== undefined ? teamsCount : 0;
    
    if (Array.isArray(fixtures)) {
        document.getElementById('stat-total-fixtures').textContent = fixtures.length;
        const completedCount = fixtures.filter(f => f.status === 'COMPLETED').length;
        const upcomingCount = fixtures.length - completedCount;
        document.getElementById('stat-completed-matches').textContent = completedCount;
        document.getElementById('stat-upcoming-matches').textContent = upcomingCount;
    } else {
        document.getElementById('stat-total-fixtures').textContent = 0;
        document.getElementById('stat-completed-matches').textContent = 0;
        document.getElementById('stat-upcoming-matches').textContent = 0;
    }
}

// ----------------------------------------------------
// Load Teams
// ----------------------------------------------------
async function loadTeams() {
    try {
        const response = await fetch('/api/teams');
        if (!response.ok) {
            throw new Error(`Server returned ${response.status}`);
        }
        const teams = await response.json();
        renderTeams(teams);
        
        // Update dashboard statistics
        const fixturesRes = await fetch('/api/fixtures');
        const fixtures = fixturesRes.ok ? await fixturesRes.json() : [];
        updateDashboard(teams.length, fixtures);
    } catch (error) {
        console.error('Error loading teams:', error);
        showAlert('Unable to connect to the server. Please make sure Spring Boot is running.', 'error');
    }
}

function renderTeams(teams) {
    const tbody = document.getElementById('teams-table-body');
    if (!teams || teams.length === 0) {
        tbody.innerHTML = '<tr><td colspan="3" class="text-center muted">No teams registered yet.</td></tr>';
        return;
    }

    tbody.innerHTML = teams.map(team => `
        <tr>
            <td><strong>#${team.id}</strong></td>
            <td><strong>${escapeHtml(team.name)}</strong></td>
            <td class="text-right">
                <button class="btn btn-danger btn-sm" onclick="deleteTeam(${team.id})">Delete</button>
            </td>
        </tr>
    `).join('');
}

// ----------------------------------------------------
// Register Team
// ----------------------------------------------------
async function addTeam(event) {
    event.preventDefault();
    const input = document.getElementById('team-name');
    const teamName = input.value.trim();

    if (!teamName) return;

    try {
        const response = await fetch('/api/teams', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ name: teamName })
        });

        const data = await response.json();

        if (!response.ok) {
            showAlert(data.message || 'Failed to register team', 'error');
            return;
        }

        showAlert('Team registered successfully.', 'success');
        input.value = '';
        loadTeams();
        loadStandings();
    } catch (error) {
        console.error('Error registering team:', error);
        showAlert('Unable to connect to the server. Please make sure Spring Boot is running.', 'error');
    }
}

// ----------------------------------------------------
// Delete Team
// ----------------------------------------------------
async function deleteTeam(teamId) {
    if (!confirm('Are you sure you want to delete this team?')) return;

    try {
        const response = await fetch(`/api/teams/${teamId}`, {
            method: 'DELETE'
        });

        if (!response.ok) {
            const data = await response.json().catch(() => ({}));
            showAlert(data.message || 'Failed to delete team', 'error');
            return;
        }

        showAlert('Team deleted successfully.', 'success');
        loadTeams();
        loadStandings();
    } catch (error) {
        showAlert('Unable to connect to the server. Please make sure Spring Boot is running.', 'error');
    }
}

// ----------------------------------------------------
// Generate Fixtures
// ----------------------------------------------------
async function generateFixtures() {
    try {
        const response = await fetch('/api/fixtures/generate', {
            method: 'POST'
        });

        const data = await response.json();

        if (!response.ok) {
            showAlert(data.message || 'Failed to generate fixtures', 'error');
            return;
        }

        showAlert('Fixtures generated successfully.', 'success');
        loadFixtures();
        loadTeams();
    } catch (error) {
        console.error('Error generating fixtures:', error);
        showAlert('Unable to connect to the server. Please make sure Spring Boot is running.', 'error');
    }
}

// ----------------------------------------------------
// Load Fixtures
// ----------------------------------------------------
async function loadFixtures() {
    try {
        const response = await fetch('/api/fixtures');
        if (!response.ok) throw new Error(`Server returned ${response.status}`);
        
        const fixtures = await response.json();
        renderFixtures(fixtures);
    } catch (error) {
        console.error('Error loading fixtures:', error);
    }
}

function renderFixtures(fixtures) {
    const container = document.getElementById('fixtures-container');
    if (!fixtures || fixtures.length === 0) {
        container.innerHTML = `
            <div class="empty-state">
                <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"><rect width="18" height="18" x="3" y="4" rx="2" ry="2"/><line x1="16" x2="16" y1="2" y2="6"/><line x1="8" x2="8" y1="2" y2="6"/><line x1="3" x2="21" y1="10" y2="10"/></svg>
                <p>No fixtures generated yet.</p>
                <span>Register teams and click "Generate Fixtures" to get started.</span>
            </div>
        `;
        return;
    }

    container.innerHTML = fixtures.map(fixture => {
        const homeName = escapeHtml(fixture.homeTeam.name);
        const awayName = escapeHtml(fixture.awayTeam.name);
        const isCompleted = fixture.status === 'COMPLETED';
        const matchObj = fixture.match || {};
        const matchId = matchObj.id || fixture.id;

        const scoreDisplay = isCompleted 
            ? `<span class="score-display">${matchObj.homeScore ?? 0} - ${matchObj.awayScore ?? 0}</span>`
            : `<span class="vs-pill">VS</span>`;

        const statusPill = isCompleted
            ? `<span class="status-pill status-pill-completed">COMPLETED</span>`
            : `<span class="status-pill status-pill-scheduled">SCHEDULED</span>`;

        const actionBtn = !isCompleted
            ? `<button class="btn btn-primary btn-sm" onclick="openResultModal(${matchId}, '${homeName.replace(/'/g, "\\'")}', '${awayName.replace(/'/g, "\\'")}')">Enter Result</button>`
            : ``;

        return `
            <div class="fixture-card ${isCompleted ? 'completed' : 'scheduled'}">
                <div class="fixture-main">
                    <div class="team-col home-team">${homeName}</div>
                    <div class="score-vs-col">${scoreDisplay}</div>
                    <div class="team-col away-team">${awayName}</div>
                </div>
                <div class="fixture-footer">
                    ${statusPill}
                    ${actionBtn}
                </div>
            </div>
        `;
    }).join('');
}

// ----------------------------------------------------
// Result Entry Modal Controls
// ----------------------------------------------------
function openResultModal(matchId, homeName, awayName) {
    document.getElementById('modal-match-id').value = matchId;
    document.getElementById('modal-home-team-name').textContent = homeName;
    document.getElementById('modal-away-team-name').textContent = awayName;
    document.getElementById('modal-home-score').value = '';
    document.getElementById('modal-away-score').value = '';
    document.getElementById('result-modal').classList.remove('hidden');
}

function closeResultModal() {
    document.getElementById('result-modal').classList.add('hidden');
}

// ----------------------------------------------------
// Submit Match Result
// ----------------------------------------------------
async function submitResult(event) {
    event.preventDefault();
    const matchId = document.getElementById('modal-match-id').value;
    const homeScore = parseInt(document.getElementById('modal-home-score').value, 10);
    const awayScore = parseInt(document.getElementById('modal-away-score').value, 10);

    if (isNaN(homeScore) || isNaN(awayScore)) {
        showAlert('Please enter valid numeric scores.', 'error');
        return;
    }

    try {
        const response = await fetch(`/api/matches/${matchId}/result`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                homeScore: homeScore,
                awayScore: awayScore
            })
        });

        const data = await response.json();

        if (!response.ok) {
            showAlert(data.message || 'Match result submission failed', 'error');
            return;
        }

        closeResultModal();
        showAlert('Match result recorded successfully.', 'success');
        loadFixtures();
        loadStandings();
        loadTeams();
    } catch (error) {
        console.error('Error submitting match result:', error);
        showAlert('Unable to connect to the server. Please make sure Spring Boot is running.', 'error');
    }
}

// ----------------------------------------------------
// Load Standings
// ----------------------------------------------------
async function loadStandings() {
    try {
        const response = await fetch('/api/standings');
        if (!response.ok) throw new Error(`Server returned ${response.status}`);
        
        const standings = await response.json();
        renderStandings(standings);
    } catch (error) {
        console.error('Error loading standings:', error);
    }
}

function renderStandings(standings) {
    const tbody = document.getElementById('standings-table-body');
    if (!standings || standings.length === 0) {
        tbody.innerHTML = '<tr><td colspan="7" class="text-center muted">No standings data available.</td></tr>';
        return;
    }

    tbody.innerHTML = standings.map((entry, index) => {
        const posClass = index === 0 ? 'pos-badge pos-top' : 'pos-badge';
        return `
            <tr>
                <td class="text-center"><span class="${posClass}">${index + 1}</span></td>
                <td><strong>${escapeHtml(entry.team.name)}</strong></td>
                <td class="text-center">${entry.played}</td>
                <td class="text-center">${entry.wins}</td>
                <td class="text-center">${entry.draws}</td>
                <td class="text-center">${entry.losses}</td>
                <td class="text-center highlight-col">${entry.points}</td>
            </tr>
        `;
    }).join('');
}

// Utility function to escape HTML special characters
function escapeHtml(str) {
    if (!str) return '';
    return String(str)
        .replace(/&/g, '&amp;')
        .replace(/</g, '&lt;')
        .replace(/>/g, '&gt;')
        .replace(/"/g, '&quot;')
        .replace(/'/g, '&#039;');
}
