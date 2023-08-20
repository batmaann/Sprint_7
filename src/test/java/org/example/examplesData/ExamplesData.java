package org.example.examplesData;

import org.apache.commons.lang3.RandomStringUtils;
import org.example.courier.CourierData;

public class ExamplesData {


    public static CourierData randomCourier(){
        return new CourierData()
                .withLogin(RandomStringUtils.randomAlphanumeric(8))
                .withPassword(RandomStringUtils.randomAlphanumeric(8))
                .withFirstName(RandomStringUtils.randomAlphanumeric(8));
    }
    public static CourierData CourierNoNameAndFirstName(){
        return new CourierData()
                .withPassword(RandomStringUtils.randomAlphanumeric(8));
    }


    public static CourierData CourierNoName(){
        return new CourierData()
                .withPassword(RandomStringUtils.randomAlphanumeric(8));
    }

    public static CourierData CourierNoPassword(){
        return new CourierData()
                .withLogin(RandomStringUtils.randomAlphanumeric(8))
                .withPassword(RandomStringUtils.randomAlphanumeric(8))
                .withFirstName(RandomStringUtils.randomAlphanumeric(8));
    }

    public static CourierData CourierAuthNoFeield(){
        return new CourierData();
    }

    public static CourierData validCourier(){
        return new CourierData()
                .withLogin("logfefefefefcin")
                .withPassword("pascccsword")
                .withFirstName("FirastName");

    }




}
