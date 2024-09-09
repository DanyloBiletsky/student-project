package edu.javaproject.studentorder.dao;

import edu.javaproject.studentorder.config.Config;
import edu.javaproject.studentorder.domain.*;
import edu.javaproject.studentorder.exception.DaoException;

import java.sql.*;
import java.time.LocalDateTime;

import static edu.javaproject.studentorder.config.Config.*;

public class StudentDaoImpl implements StudentOrderDao{

    private final static String INSERT_CHILD = "INSERT INTO jc_student_child(" +
            "student_order_id, \"с_surname\", \"с_name\", \"с_patronymic\", \"с_date_of_birth\", c_birth_certificate_number," +
            "c_register_office_id, c_date_of_receiving_birth_certificate, " +
            "\"с_post_code\", c_street_code, \"с_city\", \"с_building\", \"с_extension\", \"с_apartment\")" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    private final static String INSERT_ORDER = "INSERT INTO jc_student_order(" +
            "student_order_status, student_order_date, h_surname, h_name, h_patronymic, h_date_of_birth, h_passport_number, " +
            "h_passport_date, h_passport_department, h_post_code, h_street_code, h_city, h_building, h_extension, h_apartment, " +
            "w_surname, w_name, w_patronymic, w_date_of_birth, w_passport_number," +
            "w_passport_date, w_passport_department, w_post_code, w_street_code, w_city, w_building, w_extension, w_apartment, " +
            "marriage_certificate_id, register_office_id, marriage_date)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    // TODO refactoring make one method
    private Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(
                Config.getProperty(DB_URL),
                Config.getProperty(DB_LOGIN),
                Config.getProperty(DB_PASSWORD));
        return connection;
    }
    @Override
    public Long saveStudentOrder(StudentOrder so) throws DaoException {

        Long result = -1L;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_ORDER, new String[] {"student_order_id"}))
        {
            // Start
            statement.setInt(1, StudentOrderStatus.START.ordinal());
            statement.setTimestamp(2, java.sql.Timestamp.valueOf(LocalDateTime.now()));

            // Husband
            setParamsForAdult(statement, 3, so.getHusband());
            setParamsForAddress(statement, 10, so.getHusband());

            // Wife
            setParamsForAdult(statement, 16, so.getWife());
            setParamsForAddress(statement, 23, so.getWife());

            // Marriage
            statement.setString(29, so.getMarriageCertificateId());
            statement.setLong(30, so.getMarriageOffice().getOfficeId());
            statement.setDate(31, java.sql.Date.valueOf(so.getMarriageDate()));

            statement.executeUpdate();

            ResultSet rsGk = statement.getGeneratedKeys();
            if (rsGk.next()){
                result = rsGk.getLong(1);
            }
            rsGk.close();

            //Children Table Save Data
            saveChildren(connection, so, result);

        } catch (SQLException exception) {
            throw new DaoException(exception);
        }
        return result;
    }

    private static void saveChildren(Connection connection, StudentOrder so, Long soId) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(INSERT_CHILD)) {
            for (Child child : so.getChildren()) {
                stmt.setLong(1, soId);
                setParamsForChild(stmt, child);
                stmt.executeUpdate();
            }
        }
    }

    private static void setParamsForPerson(PreparedStatement statement, int index, Person person) throws SQLException{
        statement.setString(index, person.getSurname());
        statement.setString(index+1, person.getName());
        statement.setString(index+2, person.getPatronymic());
        statement.setDate(index+3, Date.valueOf(person.getDateOfBirth()));
    }

    private static void setParamsForAdult(PreparedStatement statement, int index, Adult adult) throws SQLException {
        setParamsForPerson(statement, index, adult);

        statement.setString(index+4, adult.getPassportNumber());
        statement.setDate(index+5, Date.valueOf(adult.getPassportIssueDate()));
        statement.setString(index+6, adult.getPassportDepartment().getOfficeName());
    }


    private static void setParamsForAddress(PreparedStatement statement, int index, Person person) throws SQLException{
        statement.setString(index, person.getAddress().getPostCode());
        statement.setString(index+1, person.getAddress().getStreet().getStreetCode());
        statement.setString(index+2, person.getAddress().getCity());
        statement.setString(index+3, person.getAddress().getBuilding());
        statement.setString(index+4, person.getAddress().getExtension());
        statement.setString(index+5, person.getAddress().getApartment());
    }
    private static void setParamsForChild(PreparedStatement statement, Child child) throws SQLException{
        setParamsForPerson(statement, 2, child);
        statement.setString(6, child.getBirthCertificateNumber());
        statement.setLong(7, child.getDepartmentOfVitalRecords().getOfficeId());
        statement.setDate(8,Date.valueOf(child.getDateOfReceivingBirthCertificate()));
        setParamsForAddress(statement, 9 ,child);
    }

}
