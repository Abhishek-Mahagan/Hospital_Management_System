package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor {

    private Connection connection;


    public Doctor(Connection connection) {
        this.connection = connection;

    }

    public void viewDoctors(){
        String query="select * from docters";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            ResultSet resultSet=preparedStatement.executeQuery();
            System.out.println("doctors..");
            System.out.println("+-------------------+--------------------------+---------------------+");
            System.out.println("|doc_id             | doc_name                 | specilization       |");
            System.out.println("+-------------------+--------------------------+---------------------+");
            while (resultSet.next())
            {
                int id=resultSet.getInt("doc_id");
                String name=resultSet.getString("doc_name");
                String specilization=resultSet.getString("specilization");
                System.out.printf("|%-26s|%-26s|%-21s|\n",id,name,specilization);
                System.out.println("+-------------------+--------------------------+---------------------+");

            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
    public  boolean getDocterById(int id)
    {
        String query="select * from docters where id= ?";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next())
            {
                return true;
            }
            else {
                return false;
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();;
        }
        return false;
    }
}
