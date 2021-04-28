package org.vaadin.addon.googlemap;

import java.util.Arrays;

import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.HasText;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;

@Tag("google-map")
@NpmPackage(value = "@tadevel/polymer-google-map", version = "^3.0.0-pre.6")
@JsModule("@tadevel/polymer-google-map/google-map.js")
public class GoogleMap extends AbstractSinglePropertyField<GoogleMap, String> implements HasStyle, HasText, HasSize {    
    private static final String NOT_SET = "not set";
    private static final long serialVersionUID = 1L;
    private static boolean geoReady = false;
    
    public GoogleMap(String k, GoogleMapMarker... markers) {
        super("value", NOT_SET, false);
        setKey(k);        
        setMarkers(markers);
    }

    public void setMarkers(GoogleMapMarker... markers) {
        Arrays.stream(markers).findFirst().ifPresent(firstMarker -> {
            setLatitude (firstMarker.getLatitude());
            setLongitude (firstMarker.getLongitude());
        });
        setMapMarkers (markers);
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

    private void setMapMarkers (GoogleMapMarker... markers) {
        String markersInnerHtml = Arrays.stream(markers).map(GoogleMapMarker::toString).reduce(" ", (s1, s2) -> s1+" "+s2);
        getElement().executeJs("this.$.objects.innerHTML = \""+markersInnerHtml+"\"");        
    }

    public void updateMarkers () {
        getElement().executeJs("this._updateObjects()");
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

}
