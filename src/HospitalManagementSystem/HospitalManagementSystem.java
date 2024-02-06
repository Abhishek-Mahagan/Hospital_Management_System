package HospitalManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {
    private static final String url="jdbc:mysql://localhost:3306/hospital";
    private static final String username="root";
    private static final String password="root";

    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();

        }
        Scanner scanner=new Scanner(System.in);
        try {
            Connection connection= DriverManager.getConnection(url,username,password);
             Patient patient=new Patient(connection,scanner);
             Doctor doctor=new Doctor(connection);
             while (true)
             {
                 System.out.println("Hospital Management System");
                 System.out.println("1.add patients");
                 System.out.println("2.view patients");
                 System.out.println("3.view doctors");
                 System.out.println("4.Book appointements");
                 System.out.println("5.exist");
                 System.out.println("Enter your choice");
                 int choice=scanner.nextInt();
                 switch (choice)
                 {
                     case 1:
                         //add patient
                         patient.addPatient();
                         System.out.println();

                     case 2:
                         //view patients
                         patient.viewPatient();
                         System.out.println();
                     case 3:
                         //view doctors
                         doctor.viewDoctors();
                         System.out.println();
                     case 4:
                         //book appointment
                         bookAppoitement(patient,doctor,connection,scanner);
                         System.out.println();

                     case 5:
                         return;
                     default:
                         System.out.println("enter valid choice");
                 }
             }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void bookAppoitement(Patient patient,Doctor doctor,Connection connection,Scanner scanner){
        System.out.println("enter patient id");
        int patient_id=scanner.nextInt();
        System.out.println("enter Doctor id");
        int doctor_id=scanner.nextInt();
        System.out.println("enter appoitment date (YYYY-MM-DD)");
        String appointment_date=scanner. next();
        if(patient.getPatientById(patient_id) && doctor.getDocterById(doctor_id))
        {
            if(checkDoctorAvaibility(doctor_id,appointment_date,connection))
            {
                String appoitementQuery="insert into  appointment(patient_id,doctor_id,appointment_date) values(?, ?, ?)";
                try{
                    PreparedStatement preparedStatement=connection.prepareStatement(appoitementQuery);
                    preparedStatement.setInt(1,patient_id);
                    preparedStatement.setInt(2,doctor_id);
                    preparedStatement.setString(3,appointment_date);
                    int affectRows=preparedStatement.executeUpdate();
                    if(affectRows>0)
                    {
                        System.out.println("Appoitment is booked");

                    }
                    else {
                        System.out.println("failed to book appoitment");
                    }
                }catch (SQLException e)

                {
                    e.printStackTrace();
                }

            }
            else
            {

            }

        }
        else {
            System.out.println("either patient or doctor doesn't exist");
        }


    }

    private static boolean checkDoctorAvaibility(int doctorId, String appointmentDate,Connection connection) {
         String query="select count(*) from appointment where doctorId= ? and appointmentDate= ?";
         try
         {
             PreparedStatement preparedStatement=connection.prepareStatement(query);
             preparedStatement.setInt(1,doctorId);
             preparedStatement.setString(2,appointmentDate);
             ResultSet resultSet=preparedStatement.executeQuery();
             if(resultSet.next())
             {
                 int count=resultSet.getInt(1);
                 if(count==0)
                 {
                     return true;
                 }
                 else {
                     return false;
                 }
             }
         }catch (SQLException e)
         {
             e.printStackTrace();
         }

        return  false;
    }
}
