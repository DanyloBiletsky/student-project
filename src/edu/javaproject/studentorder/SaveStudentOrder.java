package edu.javaproject.studentorder;

import edu.javaproject.studentorder.dao.DictionaryDaoImpl;
import edu.javaproject.studentorder.dao.StudentDaoImpl;
import edu.javaproject.studentorder.dao.StudentOrderDao;
import edu.javaproject.studentorder.domain.*;
import edu.javaproject.studentorder.domain.Child;
import edu.javaproject.studentorder.domain.Street;

import java.time.LocalDate;
import java.util.List;

public class SaveStudentOrder {

    public static void main(String[] args) throws Exception {
//        List<Street> list = new DictionaryDaoImpl().findStreets("б");
//        for (Street s : list){
//            System.out.println(s.getStreetName());
//        }
//
//        List<PassportOffice> poList = new DictionaryDaoImpl().findPassportOffices("02");
//        for (PassportOffice p : poList){
//            System.out.println(p.getOfficeName());
//        }
//
//        List<RegisterOffice> roList = new DictionaryDaoImpl().findRegisterOffices("02");
//        for (RegisterOffice r : roList){
//            System.out.println(r.getOfficeName());
//        }

//        List<CountryArea> ca1 = new DictionaryDaoImpl().findAreas("");
//        for (CountryArea ca : ca1){
//            System.out.println(ca.getAreaId() + " : " + ca.getAreaName());
//        }

        StudentOrder so = buildStudentOrder(10);
        StudentOrderDao dao = new StudentDaoImpl();
        Long id = dao.saveStudentOrder(so);
        System.out.println(id);

    }

    static long saveStudentOrder(StudentOrder studentOrder){
        System.out.println("saveStudentOrder:");
        return 199;
    }

    static void scheduleStudentMeeting(){
        System.out.println("Внёс студента в расписание на приём");
    }

    static void financingInstitutionStudentOrder(){
        System.out.println("The order has sent to the financing institution.");
    }

    public static StudentOrder buildStudentOrder(int orderID){
        StudentOrder order = new StudentOrder();

        //Дані про шлюб:
        order.setMarriageCertificateId("certificate1");
        order.setMarriageDate(LocalDate.of(2018,8,28));
        RegisterOffice ro = new RegisterOffice(2L, "marriageOfiiceName1", "OfficeAreaId111");
        order.setMarriageOffice(ro);

        Street street = new Street("01001", "First street");

        //Адреса, викликав конструктор
        Address address = new Address("03134","Sofiyvska Borshagivka", street,
                "10A", "2","28");

        //Чоловік
        Adult husband = new Adult("Biletskyi","Danylo","Vitalievich",
                LocalDate.of(2003,5,20));
        husband.setPassportNumber("" + (1000 + orderID));
        husband.setPassportIssueDate(LocalDate.of(2021,11,1));
        PassportOffice po1  = new PassportOffice(1L, "passportOffice1", "passportName1");
        husband.setPassportDepartment(po1);
        husband.setStudentId("КВ12345620");
        husband.setAddress(address);

        //Дружина
        Adult wife = new Adult("Biletska","Diana","Serhiivna",
                LocalDate.of(2005,2,24));
        wife.setPassportNumber("" + (2000 + orderID));
        PassportOffice po2  = new PassportOffice(1L, "11111", "name_name");
        wife.setPassportDepartment(po2);
        wife.setPassportIssueDate(LocalDate.of(2021,11,1));
        wife.setStudentId("KB12345987");
        wife.setAddress(address);

        //Дитина
        Child child1 = new Child("Biletska","Avrelia","Danylivna",
                LocalDate.of(2030,6,2));
        child1.setAddress(address);
        child1.setBirthCertificateNumber("AB12345" + orderID);
        child1.setDateOfReceivingBirthCertificate(LocalDate.of(2030,6,5));
        RegisterOffice ro2 = new RegisterOffice(2L, "vitalOffice1", "OfficeAreaId222");
        child1.setDepartmentOfVitalRecords(ro2);

        //Дитина
        Child child2 = new Child("Biletska","Glikeria","Danylivna",
                LocalDate.of(2030,6,2));
        child2.setAddress(address);
        child2.setBirthCertificateNumber("AB12346" + orderID);
        child2.setDateOfReceivingBirthCertificate(LocalDate.of(2030,6,5));
        child2.setDepartmentOfVitalRecords(ro2);

        //Присвоєння об'єктів з полями в поля заявки
        order.setHusband(husband);
        order.setWife(wife);
        order.addChild(child1);
        order.addChild(child2);

        return order;
    }

}
