# 🏆 League Track

### Intramural Sports Tournament Fixture and Standings Management System

League Track is a full-stack sports league management application developed using **Java, Spring Boot, Spring Data JPA, MySQL, HTML5, CSS3, and Vanilla JavaScript**.

The application is designed to manage an intramural sports tournament by allowing users to register teams, generate fixtures, record match results, and automatically maintain league standings.

---

## 📌 Features

- 🏆 Team registration and management
- 📅 Automatic round-robin fixture generation
- ⚽ Fixture and schedule management
- 📝 Match result entry
- 📊 Automatic league standings
- 🧮 Automatic points calculation
- ✅ Input validation
- ⚠️ Exception handling
- 🌐 REST API communication
- 💾 MySQL database persistence
- 📱 Responsive frontend
- 🔄 Real-time UI updates using Fetch API

---

## 🎯 Project Objective

The main objective of League Track is to simplify the management of an intramural sports tournament.

The application automates the tournament workflow:

```text
Register Teams
      ↓
Generate Fixtures
      ↓
Play Matches
      ↓
Record Match Results
      ↓
Calculate Match Outcome
      ↓
Update Points
      ↓
Update Standings
```

This reduces manual work involved in managing fixtures, match results, and league points.

---

## 🛠️ Technology Stack

### Backend

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Jakarta Validation
- Maven

### Database

- MySQL

### ORM / Persistence

- JPA
- Hibernate

### Frontend

- HTML5
- CSS3
- Vanilla JavaScript
- Fetch API

### Tools

- Git
- GitHub
- IntelliJ IDEA / VS Code / Eclipse
- MySQL
- Maven

---

## 🏗️ System Architecture

League Track follows a layered Spring Boot architecture.

```text
┌───────────────────────────────┐
│          Frontend             │
│      HTML / CSS / JS          │
└───────────────┬───────────────┘
                │
                │ HTTP / JSON
                ▼
┌───────────────────────────────┐
│         Controller            │
│          REST API             │
└───────────────┬───────────────┘
                │
                ▼
┌───────────────────────────────┐
│           Service             │
│       Business Logic          │
└───────────────┬───────────────┘
                │
                ▼
┌───────────────────────────────┐
│         Repository            │
│       Database Access         │
└───────────────┬───────────────┘
                │
                ▼
┌───────────────────────────────┐
│       JPA / Hibernate         │
└───────────────┬───────────────┘
                │
                ▼
┌───────────────────────────────┐
│            MySQL              │
└───────────────────────────────┘
```

---

## 📂 Project Structure

```text
league-track/
│
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── sports/
│       │           └── league/
│       │               │
│       │               ├── LeagueApplication.java
│       │               │
│       │               ├── entity/
│       │               │   ├── Team.java
│       │               │   ├── Fixture.java
│       │               │   ├── LeagueMatch.java
│       │               │   └── StandingsEntry.java
│       │               │
│       │               ├── repository/
│       │               │   ├── TeamRepository.java
│       │               │   ├── FixtureRepository.java
│       │               │   ├── LeagueMatchRepository.java
│       │               │   └── StandingsEntryRepository.java
│       │               │
│       │               ├── service/
│       │               │   ├── TeamService.java
│       │               │   ├── FixtureService.java
│       │               │   ├── MatchService.java
│       │               │   └── StandingsService.java
│       │               │
│       │               ├── controller/
│       │               │   ├── TeamController.java
│       │               │   ├── FixtureController.java
│       │               │   ├── MatchController.java
│       │               │   └── StandingsController.java
│       │               │
│       │               └── exception/
│       │                   ├── ResourceNotFoundException.java
│       │                   ├── InvalidMatchResultException.java
│       │                   └── GlobalExceptionHandler.java
│       │
│       └── resources/
│           │
│           ├── application.properties
│           │
│           └── static/
│               ├── index.html
│               ├── css/
│               │   └── style.css
│               └── js/
│                   └── app.js
│
├── pom.xml
├── mvnw
├── mvnw.cmd
├── .gitignore
└── README.md
```

---

## 🧩 Main Components

### Entity

The `entity` package contains Java classes that represent the application's persistent data.

Examples:

- `Team`
- `Fixture`
- `LeagueMatch`
- `StandingsEntry`

These entities are mapped to database tables using JPA.

### Repository

The repository layer handles database operations using Spring Data JPA.

Examples:

```text
TeamRepository
FixtureRepository
LeagueMatchRepository
StandingsEntryRepository
```

### Service

The service layer contains the application's business logic.

Examples:

- Team management
- Fixture generation
- Match result processing
- Points calculation
- Standings management

### Controller

The controller layer exposes REST API endpoints that communicate with the frontend.

### Exception

The exception package contains custom exceptions and centralized exception handling.

---

# 🏆 Core Features

## 1. Team Registration

Users can register teams through the application.

Example:

```text
Team Name: Chennai Warriors
```

The team is submitted through the frontend and stored in MySQL using the Spring Boot backend.

---

## 2. Team Management

Users can:

- Add teams
- View teams
- Delete teams
- Retrieve individual teams

---

## 3. Automatic Fixture Generation

League Track generates a **single round-robin schedule**.

Every team plays against every other team exactly once.

The number of fixtures is calculated using:

```text
n(n - 1)
─────────
    2
```

For example:

```text
4 teams

4 × 3
───── = 6 fixtures
  2
```

Example:

```text
Team A vs Team B
Team A vs Team C
Team A vs Team D
Team B vs Team C
Team B vs Team D
Team C vs Team D
```

Duplicate fixtures are prevented.

---

## 4. Fixture Management

The application allows users to view:

- Home team
- Away team
- Match date
- Match status
- Match result
- Result entry option

Fixtures can have statuses such as:

```text
SCHEDULED
COMPLETED
```

---

## 5. Match Result Recording

After a match is completed, the user can enter the final score.

Example:

```text
Team A       3
     VS
Team B       1
```

The backend determines whether the result is:

- Win
- Draw
- Loss

The standings are then updated automatically.

---

# 📊 Scoring System

League Track uses the following points system:

| Result | Points |
|--------|--------|
| Win    | 3      |
| Draw   | 1      |
| Loss   | 0      |

### Example

If a team has:

```text
2 Wins
1 Draw
0 Losses
```

The points are:

```text
(2 × 3) + (1 × 1) + (0 × 0)

= 7 Points
```

---

# 📈 League Standings

The application automatically maintains the league standings.

Example:

| Position | Team             | Played | Won | Draw | Lost | Points |
|----------|------------------|--------|-----|------|------|--------|
| 1        | Chennai Warriors | 3      | 2   | 1    | 0    | 7      |
| 2        | Trichy Titans    | 3      | 2   | 0    | 1    | 6      |
| 3        | Salem Strikers   | 3      | 1   | 0    | 2    | 3      |

The backend calculates and sorts the standings before sending them to the frontend.

---

# 🌐 REST API

League Track uses REST APIs for communication between the frontend and backend.

## Team APIs

### Get All Teams

```http
GET /api/teams
```

### Get Team

```http
GET /api/teams/{id}
```

### Add Team

```http
POST /api/teams
```

Example request:

```json
{
  "name": "Chennai Warriors"
}
```

### Delete Team

```http
DELETE /api/teams/{id}
```

---

## Fixture APIs

### Generate Fixtures

```http
POST /api/fixtures/generate
```

### Get Fixtures

```http
GET /api/fixtures
```

---

## Match API

### Record Match Result

```http
POST /api/matches/{id}/result
```

Example request:

```json
{
  "homeScore": 3,
  "awayScore": 1
}
```

---

## Standings API

### Get Standings

```http
GET /api/standings
```

---

# 🔄 Application Workflow

## Adding a Team

```text
User enters team name
        ↓
Click "Add Team"
        ↓
POST /api/teams
        ↓
Team Controller
        ↓
Team Service
        ↓
Team Repository
        ↓
MySQL
        ↓
Response
        ↓
Frontend updates team list
```

---

## Generating Fixtures

```text
Click "Generate Fixtures"
        ↓
POST /api/fixtures/generate
        ↓
Fixture Controller
        ↓
Fixture Service
        ↓
Generate round-robin pairs
        ↓
Save fixtures
        ↓
Return response
        ↓
Frontend displays fixtures
```

---

## Recording a Result

```text
Click "Enter Result"
        ↓
Enter scores
        ↓
POST /api/matches/{id}/result
        ↓
Validate scores
        ↓
Determine result
        ↓
Update standings
        ↓
Mark match completed
        ↓
Save result
        ↓
Refresh standings
```

---

# ✅ Validation

The application validates user input before processing it.

### Team Name

A team name must not be:

```text
null
empty
blank
```

Example validation:

```java
@NotBlank
private String name;
```

### Match Score

Scores cannot be negative.

Valid:

```text
0
1
5
10
```

Invalid:

```text
-1
-5
```

---

# ⚠️ Exception Handling

League Track uses custom exception handling to provide meaningful responses.

### ResourceNotFoundException

Used when a requested resource does not exist.

Examples:

```text
Team not found
Fixture not found
Match not found
```

### InvalidMatchResultException

Used when an invalid match result is submitted.

Examples:

```text
Negative score
Invalid match
Duplicate result
```

### GlobalExceptionHandler

Centralized exception handling is implemented using:

```java
@ControllerAdvice
```

---

# 🗃️ Database

League Track uses **MySQL** for persistent data storage.

Database:

```text
league_track
```

The application uses **JPA/Hibernate** to map Java entities to MySQL tables.

Conceptually:

```text
Java Entity
     ↓
JPA / Hibernate
     ↓
MySQL Table
```

---

# ⚙️ Configuration

The database configuration is located in:

```text
src/main/resources/application.properties
```

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/league_track
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

league.points.win=3
league.points.draw=1
league.points.loss=0
```

> **Important:** Never commit real database passwords or sensitive credentials to GitHub.

---

# 🎨 Frontend

The frontend is built using:

- HTML5
- CSS3
- Vanilla JavaScript
- Fetch API

The frontend is served by Spring Boot from:

```text
src/main/resources/static/
```

The application can therefore run from a single server.

Main URL:

```text
http://localhost:8080/
```

---

# 📱 Responsive Design

The League Track frontend is designed to work across:

- Desktop
- Laptop
- Tablet
- Mobile

The UI includes responsive:

- Navigation
- Dashboard cards
- Forms
- Tables
- Fixture cards
- Buttons

---

# 🧪 Testing

The following scenarios should be tested.

## Team Testing

```text
✓ Add valid team
✓ View teams
✓ Delete existing team
✓ Reject blank team name
✓ Handle non-existing team
```

## Fixture Testing

```text
✓ Generate fixtures
✓ Generate fixtures with valid teams
✓ Prevent duplicate fixture generation
✓ Prevent generation with fewer than two teams
✓ View fixtures
```

## Result Testing

```text
✓ Record a win
✓ Record a draw
✓ Record a 0-0 draw
✓ Reject negative scores
✓ Reject result for non-existing match
✓ Prevent duplicate result submission
✓ Update standings after result
```

---

# 📐 Business Rules

League Track follows these rules:

1. Team names cannot be blank.
2. At least two teams are required to generate fixtures.
3. Each team plays every other team once.
4. Duplicate fixtures are not allowed.
5. Match scores cannot be negative.
6. A completed match cannot be processed again.
7. A win awards 3 points.
8. A draw awards 1 point.
9. A loss awards 0 points.
10. Standings are automatically updated after a match result.
11. Important business rules are handled by the backend.

---

# 📊 Example Tournament

Consider four teams:

```text
Chennai Warriors
Coimbatore Kings
Trichy Titans
Salem Strikers
```

The system generates:

```text
Chennai Warriors vs Coimbatore Kings
Chennai Warriors vs Trichy Titans
Chennai Warriors vs Salem Strikers

Coimbatore Kings vs Trichy Titans
Coimbatore Kings vs Salem Strikers

Trichy Titans vs Salem Strikers
```

Total fixtures:

```text
4 × 3 / 2 = 6
```

After matches are completed, the system automatically calculates:

```text
Played
Won
Draw
Lost
Points
```

and displays the updated standings.

---

# 💻 Prerequisites

Before running the application, install:

### Java

Check the installed version:

```bash
java -version
```

### MySQL

Make sure MySQL Server is installed and running.

### Git

Check:

```bash
git --version
```

### Maven

The project includes the Maven Wrapper, so a separate Maven installation may not be required.

---

# 🚀 Installation

## 1. Clone the Repository

```bash
git clone https://github.com/YOUR_USERNAME/league-track.git
```

Move into the project:

```bash
cd league-track
```

---

## 2. Create the Database

Open MySQL and run:

```sql
CREATE DATABASE league_track;
```

---

## 3. Configure MySQL

Open:

```text
src/main/resources/application.properties
```

Update:

```properties
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
```

Use your local MySQL credentials.

---

# ▶️ Running the Application

### Windows

```powershell
mvnw.cmd spring-boot:run
```

### Linux / macOS

```bash
./mvnw spring-boot:run
```

Once the application starts, open:

```text
http://localhost:8080/
```

---

# 🔨 Build the Application

### Windows

```powershell
mvnw.cmd clean package
```

### Linux / macOS

```bash
./mvnw clean package
```

The generated files will be available inside:

```text
target/
```

The `target/` folder should not be committed to Git.

---

# 🔧 Troubleshooting

## MySQL Connection Error

Check:

```text
spring.datasource.url
spring.datasource.username
spring.datasource.password
```

Also make sure MySQL Server is running.

---

## Port 8080 Already in Use

Another application may already be using port 8080.

Stop the application using the port or configure another port.

---

## Frontend Cannot Connect to Backend

Make sure Spring Boot is running and open:

```text
http://localhost:8080/
```

The frontend uses relative API paths such as:

```text
/api/teams
/api/fixtures
/api/standings
```

---

## Cannot Generate Fixtures

Make sure at least two teams have been registered.

---

## Duplicate Fixtures

League Track prevents duplicate fixture generation.

---

## Cannot Submit Result

Check that:

- The match exists.
- The match has not already been completed.
- Scores are valid.
- Scores are not negative.

---

# 🧠 Concepts Demonstrated

This project demonstrates practical knowledge of:

- Java
- Spring Boot
- Spring Web
- REST API
- HTTP methods
- JPA
- Hibernate
- ORM
- MySQL
- Maven
- CRUD operations
- Entity relationships
- Repository pattern
- Service layer
- Controller layer
- Jakarta Validation
- Exception handling
- JSON
- Fetch API
- Round-robin scheduling
- Database persistence

---

# 📚 Important Java / Spring Annotations

The project uses annotations such as:

```java
@SpringBootApplication
@Entity
@Id
@GeneratedValue
@ManyToOne
@OneToOne
@Repository
@Service
@RestController
@RequestMapping
@GetMapping
@PostMapping
@DeleteMapping
@RequestBody
@PathVariable
@Valid
@NotBlank
@NotNull
@PositiveOrZero
@ControllerAdvice
@ExceptionHandler
```

Annotations provide metadata and instructions that are used by Java, Spring Boot, JPA, and the validation framework.

---

# 🔄 Complete Data Flow

```text
                USER
                  │
                  ▼
             WEB BROWSER
                  │
                  ▼
          HTML / CSS / JS
                  │
                  ▼
            Fetch API
                  │
                  │ HTTP
                  ▼
          Spring Controller
                  │
                  ▼
             Service
                  │
                  ▼
            Repository
                  │
                  ▼
          JPA / Hibernate
                  │
                  ▼
               MySQL
                  │
                  ▼
          JSON Response
                  │
                  ▼
            Web Browser
                  │
                  ▼
            Updated UI
```

---

# 🔮 Future Enhancements

Possible future improvements include:

- User authentication
- Admin and user roles
- Multiple tournaments
- Multiple sports
- Team logos
- Player management
- Player statistics
- Venue management
- Tournament history
- Search and filtering
- PDF export
- Email notifications
- Tournament archive
- Advanced analytics
- Cloud deployment

These are potential future enhancements and are not required for the core project.

---

# 🎓 Learning Outcomes

Through League Track, the following full-stack development concepts are demonstrated:

### Backend

Developing REST APIs using Spring Boot.

### Database

Using MySQL for persistent data storage.

### ORM

Mapping Java objects to relational database tables using JPA/Hibernate.

### Frontend

Building a responsive web interface using HTML, CSS, and Vanilla JavaScript.

### API Integration

Connecting the frontend with Spring Boot REST APIs using Fetch API.

### Business Logic

Implementing:

- Fixture generation
- Match result processing
- Points calculation
- Standings management

### Validation

Preventing invalid input and invalid operations.

### Exception Handling

Returning meaningful error responses.

---

# 📌 Project Summary

League Track is a full-stack intramural sports tournament management system that provides:

```text
Team Management
       +
Fixture Generation
       +
Match Result Management
       +
Automatic Points Calculation
       +
League Standings
       =
Complete Tournament Management
```

The project combines **Java, Spring Boot, JPA, MySQL, HTML, CSS, and JavaScript** into a complete full-stack application.

---

# 👨‍💻 Author

**League Track**

Intramural Sports Tournament Fixture and Standings Management System

Built as a full-stack Java Spring Boot project.

---

## ⭐ League Track

> **Manage Teams. Generate Fixtures. Track Results. Follow the Standings.**