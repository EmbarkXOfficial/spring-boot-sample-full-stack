# EmbarkX Full-Stack Demo

A minimal full-stack app used to demonstrate deploying to a VPS with Docker and CI/CD.

- `backend/` – Spring Boot 4.1 (Java 25, Maven)
- `frontend/` – React 19 + Vite 8, plain CSS

## Prerequisites

- Java 25
- Node.js 20.19+ (or 22.12+)

You don't need Maven installed – the backend ships with the Maven Wrapper (`mvnw` / `mvnw.cmd`).

## Run locally

Use two terminals: one for the backend, one for the frontend.

### 1. Backend (http://localhost:8080)

**macOS / Linux**

```bash
cd backend
./mvnw spring-boot:run
```

**Windows (PowerShell)**

```powershell
cd backend
.\mvnw.cmd spring-boot:run
```

**Windows (Command Prompt)**

```bat
cd backend
mvnw.cmd spring-boot:run
```

### 2. Frontend (http://localhost:5173)

Same on every OS:

```bash
cd frontend
npm install
npm run dev
```

Open http://localhost:5173. The Vite dev server proxies `/api` to the backend on port 8080.

## Endpoints

| Endpoint               | Description                                |
| ---------------------- | ------------------------------------------ |
| `GET /api/message`     | Returns `message`, `version`, `serverTime` |
| `GET /actuator/health` | Health check                               |

## Configuration (environment variables)

| Variable      | Default              |
| ------------- | -------------------- |
| `APP_MESSAGE` | `Hello from EmbarkX` |
| `APP_VERSION` | `local`              |

Variables are read **once, when the backend starts**. Set them in the **same terminal** you start the backend from, then restart the backend so the new values take effect.

### macOS / Linux (bash, zsh)

One-off, for a single run:

```bash
APP_MESSAGE="Hello from my Mac" APP_VERSION="1.0.0" ./mvnw spring-boot:run
```

Or for the whole terminal session (note the `export` – without it the variable is not passed to the app):

```bash
export APP_MESSAGE="Hello from my Mac"
export APP_VERSION="1.0.0"
./mvnw spring-boot:run
```

### Windows (PowerShell)

```powershell
$env:APP_MESSAGE = "Hello from Windows"
$env:APP_VERSION = "1.0.0"
.\mvnw.cmd spring-boot:run
```

### Windows (Command Prompt)

No quotes around the value – CMD would include them in the message.

```bat
set APP_MESSAGE=Hello from Windows
set APP_VERSION=1.0.0
mvnw.cmd spring-boot:run
```

### Any OS: pass the values as arguments

This doesn't rely on the shell's environment at all:

```bash
./mvnw spring-boot:run -Dspring-boot.run.arguments="--APP_MESSAGE='Hello from args' --APP_VERSION=1.0.0"
```

On Windows, use `.\mvnw.cmd` (PowerShell) or `mvnw.cmd` (CMD) in place of `./mvnw`.

### Running the built JAR

```bash
cd backend
./mvnw clean package

# macOS / Linux
APP_MESSAGE="Hello from the JAR" APP_VERSION="1.0.0" java -jar target/backend-0.0.1-SNAPSHOT.jar
```

```powershell
# Windows (PowerShell)
$env:APP_MESSAGE = "Hello from the JAR"; $env:APP_VERSION = "1.0.0"
java -jar target\backend-0.0.1-SNAPSHOT.jar
```

### Running from IntelliJ / VS Code

IDE run configurations do **not** inherit variables set in a terminal. Add them in the run configuration instead:

- **IntelliJ:** Run → Edit Configurations → `BackendApplication` → Environment variables → `APP_MESSAGE=Hello from IntelliJ;APP_VERSION=1.0.0`
- **VS Code:** add `"env": { "APP_MESSAGE": "Hello from VS Code", "APP_VERSION": "1.0.0" }` to the launch configuration in `.vscode/launch.json`

### Verify

```bash
curl http://localhost:8080/api/message
```

(PowerShell: `Invoke-RestMethod http://localhost:8080/api/message`)

### Still seeing the defaults?

An older backend instance is probably still running on port 8080. The new one then fails with *"Port 8080 was already in use"*, and you keep talking to the old one. Stop it, then start again:

- **macOS / Linux:** `lsof -i :8080` to find the PID, then `kill <PID>`
- **Windows:** `netstat -ano | findstr :8080` to find the PID, then `taskkill /PID <PID> /F`

## Build

```bash
cd backend && ./mvnw clean package   # -> backend/target/backend-0.0.1-SNAPSHOT.jar
cd frontend && npm run build         # -> frontend/dist/
```

On Windows, use `mvnw.cmd clean package` for the backend.