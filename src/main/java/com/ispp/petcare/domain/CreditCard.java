package com.ispp.petcare.domain;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Embeddable;

/**
 * Created by valentin on 14/03/2016.
 */
@Embeddable
public class CreditCard {

        //Constructor-------------------------------------------------
        public CreditCard(){
            super();
        }

        //Attributes---------------------------------------------
        @NotBlank
        private String holderName;
        @NotBlank
        private String brandName;
        @NotBlank
        @CreditCardNumber
        private String number;
        @Range(min = 1, max = 12)
        private int expirationMonth;
        @Range(min = 2015)
        private int expirationYear;
        @Range(min = 100, max = 999)
        private int CVV;



        public String getHolderName() {
            return holderName;
        }

        public void setHolderName(String holderName) {
            this.holderName = holderName;
        }


        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }



        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        @Range(min = 1, max = 12)
        public int getExpirationMonth() {
            return expirationMonth;
        }

        public void setExpirationMonth(int expirationMonth) {
            this.expirationMonth = expirationMonth;
        }

        @Range(min = 2015)
        public int getExpirationYear() {
            return expirationYear;
        }

        public void setExpirationYear(int expirationYear) {
            this.expirationYear = expirationYear;
        }


        public int getCVV() {
            return CVV;
        }

        public void setCVV(int cVV) {
            CVV = cVV;
        }


        //toString---------------------------------------
        @Override
        public String toString() {
            return "CreditCard [holderName=" + holderName + ", brandName="
                + brandName + ", number=" + number + ", expirationMonth="
                + expirationMonth + ", expirationYear=" + expirationYear
                + ", CVV=" + CVV + "]";
        }








}
