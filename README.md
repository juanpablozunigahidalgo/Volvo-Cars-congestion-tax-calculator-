# Instruction from Volvo.

This is a home assigment for the company Volvo Cars. I got the invitation for the position "Full Stack Developer" at Volvo Cars Finance. In october 2024.The requirement was to make this test in JAVA. I used cursor to program this solution. If you want to read the full instruction from Volvo. PLease read Assigment.md. 

# Overal Solution Logic

This application includes both backend and frontend solution. This to calculate the tax paid by cars. I simulated a artificial image recognition system. That returns a JSON object. Such JSON can be accessible by the cloud. In this example. I storaged the object in JSONBIN IO. The back end calls such information. And calculates the tax paid that later is displayed in the frontend. 

The backend fetches car data stored in JSONBin.io as a JSON object. This data represents car images "scanned" by an imaginary camera, which detects the car type and registration number. The backend processes this information to calculate the congestion tax based on the time the vehicle was identified. The frontend then displays the total tax paid by regular vehicles and the tax breakdown by individual registration numbers.

Please read "exampleAIcameras.json" to understand what the cameras data gives. Also. If you want to see the draft solution please see the images "localhost-3000-view" as well as "localhost8080-view".

# Instruction to run this solution on your computer.

Prerequisites
Ensure that you have installed the following:

Node.js and npm (for the frontend)
Java JDK 11+ and Apache Maven (for the backend)
Backend Setup (Spring Boot)
Navigate to the Backend Directory:

bash
Copia codice
cd backend
Configure JSONBin API Key
Ensure that the API key in the application.properties file is valid. The configuration file is located at:

css
Copia codice
src/main/resources/application.properties
Build and Run the Backend Server: Use Maven to start the Spring Boot application.

bash
Copia codice
mvn spring-boot:run
This will start the backend server on port 8080 (default).
Verify Backend Server: Visit http://localhost:8080/api/tax/calculate to confirm the backend is running. This endpoint will fetch the car information from JSONBin and calculate taxes.

Frontend Setup (React)
Navigate to the Frontend Directory:

bash
Copia codice
cd frontend
Install Dependencies: Run the following command to install necessary packages.

bash
Copia codice
npm install
Start the Frontend Development Server:

bash
Copia codice
npm start
This will start the frontend on http://localhost:3000, where it will connect to the backend to fetch tax data.
View the Application: Open a browser and go to http://localhost:3000 to see the application interface.

The frontend will display:
Total tax paid by regular vehicles.
A breakdown of tax paid by each registration number.

# Summary of Operation
Data Source (JSONBin): The backend fetches JSON data simulating car images read by a camera, with vehicle type and registration number.
Tax Calculation: The backend processes this data, calculates tax based on time rules, and serves this information to the frontend.
Frontend Display: The frontend shows a summary of the tax paid, both in total and per vehicle registration number.
If any issues arise, please ensure your application.properties file contains the correct API URL and key configuration for JSONBin.


