package org.vaadin.addon.googlemap;

public class GoogleMapMarker {

    public enum Animation {
        DROP, BOUNCE;
    }

    private boolean draggable = false;
    private double latitude;
    private double longitude;
    private Animation animation;
    private String contentText;

    private GoogleMapMarker () {}

    public boolean isDraggable() {
        return draggable;
    }

    public void setDraggable(boolean draggable) {
        this.draggable = draggable;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Animation getAnimation() {
        return animation;
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    @Override
    public String toString() {
        return "<google-map-marker "+ (draggable ? "draggable" : "") +" latitude='"+latitude+"' longitude='"+longitude+"' animation='"+animation.name()+"'>"+contentText+"</google-map-marker>";
    }

    public static class GoogleMapMarkerBuilder {

        public boolean draggable;
        public double latitude;
        public double longitude;
        public Animation animation;
        public String contentText;
        
        public GoogleMapMarkerBuilder (double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public GoogleMapMarkerBuilder withDraggable (boolean draggable) {
            this.draggable = draggable;
            return this;
        }

        public GoogleMapMarkerBuilder withAnimation (Animation animation) {
            this.animation = animation;
            return this;
        }

        public GoogleMapMarkerBuilder withContentText (String contentText) {
            this.contentText = contentText;
            return this;
        }

        public GoogleMapMarker build () {
            GoogleMapMarker marker = new GoogleMapMarker();
            marker.setDraggable(this.draggable);
            marker.setLatitude(this.latitude);
            marker.setLongitude(this.longitude);            
            marker.setAnimation(this.animation);
            marker.setContentText(this.contentText);

            return marker;
        }

    }

}