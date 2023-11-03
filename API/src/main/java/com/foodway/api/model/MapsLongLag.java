package com.foodway.api.model;

public class MapsLongLag {

        private String longtitude;
        private String latitude;

        public MapsLongLag() {
        }

        public MapsLongLag(String longtitude, String latitude) {
            this.longtitude = longtitude;
            this.latitude = latitude;
        }

        public String getLongtitude() {
            return this.longtitude;
        }

        public void setLongtitude(String longtitude) {
            this.longtitude = longtitude;
        }

        public String getLatitude() {
            return this.latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

    @Override
    public String toString() {
        return "MapsLongLag{" +
                "longtitude='" + longtitude + '\'' +
                ", latitude='" + latitude + '\'' +
                '}';
    }
}
