package org.vaadin.addon.googlemap.ui;

import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import org.vaadin.addon.googlemap.GoogleMap;
import org.vaadin.addon.googlemap.GoogleMapMarker;
import org.vaadin.addon.googlemap.GoogleMapMarker.Animation;
import org.vaadin.addon.googlemap.GoogleMapMarker.GoogleMapMarkerBuilder;;

@Route("")
@JsModule("./styles/shared-styles.js")
public class DemoView extends VerticalLayout
{

    public DemoView()
    {
        FlexLayout div = new FlexLayout();
        div.getStyle().set("flex-direction", "column");
        div.setWidth("40em");
        div.setHeight("40em");
        div.add(new Label("Vaadin google map"));     
        
        GoogleMapMarker marker1 =
        new GoogleMapMarkerBuilder(37.78d, -122.4d)
        .withAnimation(Animation.DROP)
        .withContentText("Rua legal, 46")
        .withDraggable(true)
        .build();

        GoogleMapMarker marker2 =
        new GoogleMapMarkerBuilder(50d, 10d)
        .withAnimation(Animation.DROP)
        .withContentText("Rua firmeza, 49")
        .withDraggable(true)
        .build();
        GoogleMap map = new GoogleMap("API-KEY", marker2, marker1);
        map.setZoom(7);
        map.setFitToMarkers(true);
        div.add(map);

        add(div);
    }
}
