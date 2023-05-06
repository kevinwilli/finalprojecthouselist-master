package edu.guilford;

import java.util.ArrayList;
import java.util.Random;

public class PropertyLists {

    public static class PropertyList {
        // attributes of properties
        private String address;
        private String city;
        private String state;
        private String zip;
        private String price;
        private String owner;
        private String status;

        private String duration;
        private String type;
        private String ownerID;
        private String propertyID;

        // constructor for property
        public PropertyList(String address, String city, String state, String zip,
                String price, String owner, String status, String duration, String type, String ownerID,
                String propertyID) {
            this.address = address;
            this.city = city;
            this.state = state;
            this.zip = zip;
            this.price = price;
            this.owner = owner;
            this.status = status;
            this.duration = duration;
            this.type = type;
            this.ownerID = ownerID;
            this.propertyID = propertyID;
        }

        // static method to generate a list of random properties
        public static ArrayList<PropertyList> generateRandomPropertyList(int numProperties) {
            ArrayList<PropertyList> properties = new ArrayList<>();
            Random rand = new Random();
            // address
            String[] address = { "123 Green St", "456 Guilford St", "789 Enlglish St", "1011 East St", "1213 North St",
                    "1415 South St",
                    "1617 Bryan St", "1819 Hobs St", "2021 Rachels St", "2223 Founders St", "2425 Wendover St" };
            String[] cities = { "New York", "Los Angeles", "Chicago", "Houston", "Phoenix", "Philadelphia",
                    "San Antonio", "San Diego", "Dallas", "San Jose", "Greensboro" };
            String[] states = { "NY", "CA", "IL", "TX", "AZ", "PA", "TX", "CA", "TX", "CA", "NC" };
            String[] owner = { "Tyler Smith", "Jane lowk", "Axel Sandoval", "Kevin Buikpor", "Rob Whitnell",
                    "Rogelio Perez",
                    "Sam Williams", "lex Mill", "Oscar Brown", "Carl Brown", "Jim Jones", "alex Jones" };
            String[] statuses = { "For sale", "For rent", "under contract", "sold" };
            String[] types = { "single family home", "condo", "apartment", "townhouse" };

            for (int i = 0; i < numProperties; i++) { // loop to add random properties to list
                String randomAddress = address[rand.nextInt(address.length)];
                String city = cities[rand.nextInt(cities.length)];
                String state = states[rand.nextInt(states.length)];
                String zip = String.format("%05d", rand.nextInt(100000));
                String price = String.format("$%.2f", rand.nextDouble() * 100000);
                String ownerS = owner[rand.nextInt(owner.length)];
                String status = statuses[rand.nextInt(statuses.length)];
                String duration = String.valueOf(rand.nextInt(12) + 1) + " months";
                String type = types[rand.nextInt(types.length)];
                String ownerID = String.valueOf(rand.nextInt(1000));
                String propertyID = String.valueOf(rand.nextInt(10000));

                PropertyList property = new PropertyList(randomAddress, city, state, zip, price, ownerS, status,
                        duration,
                        type, ownerID, propertyID);
                properties.add(property);
            }

            return properties;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getZip() {
            return zip;
        }

        public void setZip(String zip) {
            this.zip = zip;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getOwnerID() {
            return ownerID;
        }

        public void setOwnerID(String ownerID) {
            this.ownerID = ownerID;
        }

        public String getPropertyID() {
            return propertyID;
        }

        public void setPropertyID(String propertyID) {
            this.propertyID = propertyID;
        }

        // to method
        public String toString() {
            return "PropertyList [address=" + address + ", city=" + city + ", state=" + state + ", zip=" + zip
                    + ", price="
                    + price + ", owner=" + owner + ", status=" + status + ", duration=" + duration + ", type=" + type
                    + ", ownerID=" + ownerID + ", propertyID=" + propertyID + "]";
        }

    }

    // create a point algorithm that attributes ponts to each property
    public static int pontAlgorithm(PropertyList property) {
        int pont = 0;
        if (property.getStatus().equals("For sale")) {
            pont += 10;
        } else if (property.getStatus().equals("For rent")) {
            pont += 5;
        } else if (property.getStatus().equals("under contract")) {
            pont += 2;
        } else if (property.getStatus().equals("sold")) {
            pont += 1;
        }
        if (property.getType().equals("single family home")) {
            pont += 10;
        } else if (property.getType().equals("condo")) {
            pont += 5;
        } else if (property.getType().equals("apartment")) {
            pont += 2;
        } else if (property.getType().equals("townhouse")) {
            pont += 1;
        }
        return pont;
    }

    // sort the list of properties by points into a new list
    public static ArrayList<PropertyList> sortProperties(ArrayList<PropertyList> properties) {
        ArrayList<PropertyList> sortedProperties = new ArrayList<>();
        for (int i = 0; i < properties.size(); i++) {
            int max = 0;
            int index = 0;
            for (int j = 0; j < properties.size(); j++) {
                if (pontAlgorithm(properties.get(j)) > max) {
                    max = pontAlgorithm(properties.get(j));
                    index = j;
                }
            }
            sortedProperties.add(properties.get(index));
            properties.remove(index);
        }
        return sortedProperties;
    }
}
