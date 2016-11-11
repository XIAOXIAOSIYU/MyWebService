package com.jackzhao.www.mywebservice;

import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.sql.Date;

public class CallSoap {

    public String GetPersons() {

        String response = "";

        String SOAP_ACTION = "http://tempuri.org/HelloWorld";
        String OPERATION_NAME = "HelloWorld";
        String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";
        String SOAP_ADDRESS = "http://192.168.1.233:8080/MyWebService.asmx";

        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        try {
            HttpTransportSE transport = new HttpTransportSE(SOAP_ADDRESS);
            transport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            transport.debug = true;
            transport.call(SOAP_ACTION, envelope);

            SoapObject so = (SoapObject) envelope.bodyIn;
            response = so.getProperty(0).toString();

        } catch (Exception ex) {
            ex.printStackTrace();
            response = ex.getMessage();
        }

        return response;
    }

    public Person GetPersonDetails(int person_id) {

        Person person = new Person();

        String SOAP_ACTION = "http://tempuri.org/GetPerson";
        String OPERATION_NAME = "GetPerson";
        String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";
        String SOAP_ADDRESS = "http://192.168.1.233:8080/MyWebService.asmx";

        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        PropertyInfo pi_person_id = new PropertyInfo();
        pi_person_id.setName("person_id");
        pi_person_id.setValue(person_id);
        pi_person_id.setType(int.class);
        request.addProperty(pi_person_id);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        try {
            HttpTransportSE transport = new HttpTransportSE(SOAP_ADDRESS);
            transport.debug = true;
            transport.call(SOAP_ACTION, envelope);
            SoapObject so = (SoapObject) envelope.bodyIn;
            SoapPrimitive sp = (SoapPrimitive) so.getProperty(0);

            String response = sp.toString();
            JSONObject json = new JSONObject(response);

            person.setPerson_Id(Integer.parseInt(json.get("Person_Id").toString()));
            person.setPerson_Old_Id(Integer.parseInt(json.get("Person_Old_Id").toString()));
            person.setPerson_Category_Id(Integer.parseInt(json.get("Person_Category_Id").toString()));
            person.setPerson_Type_Ids(json.get("Person_Type_Ids").toString());
            person.setPerson_Name_Cn(json.get("Person_Name_Cn").toString());
            person.setPerson_Name_En(json.get("Person_Name_En").toString());
            person.setPerson_Company_Cn(json.get("Person_Company_Cn").toString());
            person.setPerson_Company_En(json.get("Person_Company_En").toString());
            person.setPerson_Title(json.get("Person_Title").toString());
            person.setPerson_Description(json.get("Person_Description").toString());
            person.setPerson_Description2(json.get("Person_Description2").toString());
            person.setPerson_Special_Description(json.get("Person_Special_Description").toString());
            person.setPerson_Tag(json.get("Person_Tag").toString());
            person.setPerson_Photo1(json.get("Person_Photo1").toString());
            person.setPerson_Photo2(json.get("Person_Photo2").toString());
            person.setPerson_Homepage(json.get("Person_Homepage").toString());
            person.setPerson_Review_Count(Integer.parseInt(json.get("Person_Review_Count").toString()));
            person.setPerson_Comments_Count(Integer.parseInt(json.get("Person_Comments_Count").toString()));
            person.setPerson_Votes_Count(Integer.parseInt(json.get("Person_Votes_Count").toString()));
            person.setPerson_Order(Integer.parseInt(json.get("Person_Order").toString()));
            person.setPerson_Status(Integer.parseInt(json.get("Person_Status").toString()));
            person.setCreate_User_Id(Integer.parseInt(json.get("Create_User_Id").toString()));
            person.setCreate_User_Name(json.get("Create_User_Name").toString());
            person.setCreate_User_Ip(json.get("Create_User_Ip").toString());
            person.setCreate_Date(Date.valueOf(json.get("Create_Date").toString()));
            person.setUpdate_User_Id(Integer.parseInt(json.get("Update_User_Id").toString()));
            person.setUpdate_User_Name(json.get("Update_User_Name").toString());
            person.setUpdate_User_Ip(json.get("Update_User_Ip").toString());
            person.setUpdate_Date(Date.valueOf(json.get("Update_Date").toString()));

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return person;
    }
}
