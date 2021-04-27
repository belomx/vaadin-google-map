package org.vaadin.addon.googlemap;

import java.util.Arrays;

import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.DomEvent;
import com.vaadin.flow.component.EventData;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.HasText;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.shared.Registration;

@Tag("google-map")
@NpmPackage(value = "@tadevel/polymer-google-map", version = "^3.0.0-pre.6")
@JsModule("@tadevel/polymer-google-map/google-map.js")
public class GoogleMap extends AbstractSinglePropertyField<GoogleMap, String> implements HasStyle, HasText, HasSize {    
    private static final String NOT_SET = "not set";
    private static final long serialVersionUID = 1L;
    //private static final PropertyDescriptor<String,String> placeJSON = PropertyDescriptors.propertyWithDefault("placeJSON", "not entered");
    private static boolean geoReady = false;
    
    public GoogleMap(String k, GoogleMapMarker... markers) {
        super("value", NOT_SET, false);
        setKey(k);
        setFitToMarkers(true);
        Arrays.stream(markers).findFirst().ifPresent(firstMarker -> {
            setLatitude (firstMarker.getLatitude());
            setLongitude (firstMarker.getLongitude());
        });
        setMarkers (markers);
    }

    public void setZoom (double zoom) {
        getElement().setProperty("zoom", zoom);
    }

    public void removeLatitude () {
        getElement().removeProperty("latitude");
    }

    public void removeLongitude () {
        getElement().removeProperty("longitude");
    }

    public void setLatitude (double latitude) {
        getElement().setProperty("latitude", latitude);
    }

    public void setLongitude (double longitude) {
        getElement().setProperty("longitude", longitude);
    }

    public void setMarkers (GoogleMapMarker... markers) {
        String markersInnerHtml = Arrays.stream(markers).map(GoogleMapMarker::toString).reduce(" ", (s1, s2) -> s1+" "+s2);
        getElement().executeJs("this.$.objects.innerHTML = \""+markersInnerHtml+"\"");
    }
    
    public void setKey(String k)
    {
        getElement().setProperty("apiKey",k);
    }
    
    public void setFitToMarkers(Boolean k)
    {
        getElement().setProperty("fitToMarkers", k);
    }
    
    @Override
    public void setText(String text) {
        setLabel(text);
    }

    @Override
    public String getText() {
        return getElement().getProperty("label");
    }
    
    private void setLabel(String label) {
        getElement().setProperty("label", label);
    }
    
    public void setPlaceholder(String placeholder) {
        getElement().setProperty("placeholder",
                placeholder == null ? "" : placeholder);
    }
    
    public void setRequired(boolean required) {
        getElement().setProperty("required", required);
    }
    
    /** 
     * Sets the desired language for the input and the autocomplete list.
     * Normally, Google Places Autocomplete defaults to the browser default language.
     * This value allows the language to be set to a desired language regardless of the browser default.
     * 
     * For a list of language codes supported see https://developers.google.com/maps/faq#languagesupport
     * 
     * *** the value should not be modified after the element is loaded ***
     */
    public void setLanguage(String language) {
        getElement().setProperty("language", language);
    }
    
    public void fillValue(String placeString)
    {
        String comm = "this.fillValue($0)";
        if(geoReady) {
            getElement().executeJs(comm, placeString);
        }        
    }
    
    /*
    public String getPlace()
    {
        return placeJSON.get(this);
    }
    
    public void setPlace(String place)
    {
        placeJSON.set(this, place);
    }*/
    
    /*
    public Optional<PlaceResponse> getPlaceResponse () {        
        if (isPlaceReady()) {
            String addressJson = getPlace();
            Gson gsonParser = new GsonBuilder().create();
            return Optional.of(gsonParser.fromJson(addressJson, PlaceResponse.class));
        }
        return Optional.empty();
    }
    
    private boolean isPlaceReady() {
        String addressJson = getPlace();
        return addressJson != null && !addressJson.isEmpty() && !NOT_SET.equals(addressJson);
    }*/
        
    @DomEvent("api-loaded")
    public static class GeoReadyEvent extends ComponentEvent<GoogleMap>
    {
        private String value;

        public GeoReadyEvent(GoogleMap source,boolean fromClient,@EventData("event.detail.text") String value)
        {
            super(source,fromClient);
            this.value=value;
            geoReady=true;
        }
        
        public String getValue() {
            return value;
        }
    }
    public Registration addGeoReadyEventListener(ComponentEventListener<GeoReadyEvent> listener)
    {
        return addListener(GeoReadyEvent.class, listener);
    }
    
    /*
    @SuppressWarnings("serial")
    @DomEvent("change-placejson-complete")
    public static class ChangePlacejsonCompleteEvent extends ComponentEvent<GoogleMap>
    {
        private PlaceResponse reponse;

        public ChangePlacejsonCompleteEvent(GoogleMap source,boolean fromClient,@EventData("event.detail.placeJSON") String placeJSON)
        {
            super(source,fromClient);
            source.setPlace(placeJSON);
            reponse = source.getPlaceResponse().orElse(null);
        }

        public PlaceResponse getPlace() {
            return reponse;
        }
    }
    public Registration addChangePlacejsonCompleteEventListener(ComponentEventListener<ChangePlacejsonCompleteEvent> listener)
    {
        return addListener(ChangePlacejsonCompleteEvent.class, listener);
    }*/
        
    @DomEvent("input-change")
    public static class ValueChangeEvent extends ComponentEvent<GoogleMap>
    {
        private String value;

        public ValueChangeEvent(GoogleMap source,boolean fromClient,@EventData("event.detail.text") String value)
        {
            super(source,fromClient);
            this.value=value;
        }
        
        public String getValue() {
            return value;
        }
    }
    public Registration addValueChangeEventListener(ComponentEventListener<ValueChangeEvent> listener)
    {
        return addListener(ValueChangeEvent.class, listener);
    }

    @DomEvent("change-complete")
    public static class ValueCompleteEvent extends ComponentEvent<GoogleMap>
    {
        private String value;

        public ValueCompleteEvent(GoogleMap source,boolean fromClient,@EventData("event.detail.text") String value)
        {
            super(source,fromClient);
            this.value=value;
        }
        
        public String getValue() {
            return value;
        }
    }
    public Registration addValueCompleteEventListener(ComponentEventListener<ValueCompleteEvent> listener)
    {
        return addListener(ValueCompleteEvent.class, listener);
    }

    @DomEvent("click")
    public static class ClickEvent extends ComponentEvent<GoogleMap>
    {
        private String value;

        public ClickEvent(GoogleMap source,boolean fromClient,@EventData("event.detail.text") String value)
        {
            super(source,fromClient);
            this.value=value;
        }
        
        public String getValue() {
            return value;
        }
    }
    public Registration addClickEventListener(ComponentEventListener<ClickEvent> listener)
    {
        return addListener(ClickEvent.class, listener);
    }

}
