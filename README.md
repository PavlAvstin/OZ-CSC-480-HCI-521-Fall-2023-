# OZ-CSC-480-HCI-521-Fall-2023-

## Requirements to Run
- Docker

## Build Instructions for Linux/Mac
**Step 1:** Open the project in command line <br>
**Step 2:** Change directory to buildAutomation <br>
```cd buildAutomation``` <br>
**Step 3:** Call the startup script <br>
```./startupEverything``` <br>
**Step 4** When done shutdown all the containers <br>
```./stopEverything``` <br>

## Build Instructions for Windows
**Step 1:** Open the project in command line <br>
**Step 2:** Run the docker compose up command <br>
```docker compose up --build -d``` <br>
**Step 3:** When done shutdown shutdown all the containers <br>
```docker compose down``` <br>