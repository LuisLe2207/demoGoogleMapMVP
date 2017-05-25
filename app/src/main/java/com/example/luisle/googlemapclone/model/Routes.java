package com.example.luisle.googlemapclone.model;

import java.util.List;

/**
 * Created by LuisLe on 5/25/2017.
 */

public class Routes {
    private List<Legs> legs;
    private Overview_polyline overview_polyline;

    public List<Legs> getLegs() {
        return legs;
    }

    public void setLegs(List<Legs> legs) {
        this.legs = legs;
    }

    public Overview_polyline getOverview_polyline() {
        return overview_polyline;
    }

    public void setOverview_polyline(Overview_polyline overview_polyline) {
        this.overview_polyline = overview_polyline;
    }

    public Routes(List<Legs> legs, Overview_polyline overview_polyline) {
        this.legs = legs;
        this.overview_polyline = overview_polyline;
    }
}
