# OZ-CSC-480-HCI-521-Fall-2023-

## Requirements to Run
- Docker

## Build Instructions for Linux/Mac
**Step 1:** Open the project in command line <br>
**Step 2:** Change directory to buildAutomation <br>
```
cd buildAutomation
```
**Step 3:** Call the startup script <br>
```
./startupEverything
```
**Step 4** When done shutdown all the containers <br>
```
./stopEverything
```

## Build Instructions for Windows
**Step 1:** Open the project in command line <br>
**Step 2:** Run the docker compose up command <br>
```
docker compose up --build -d
```
**Step 3:** When done shutdown all the containers <br>
```
docker compose down
```