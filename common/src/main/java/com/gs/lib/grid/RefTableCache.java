package com.gs.lib.grid;

import com.gigaspaces.document.SpaceDocument;
import com.j_spaces.core.IJSpace;
import com.j_spaces.core.client.SQLQuery;
import org.openspaces.core.space.cache.LocalViewSpaceConfigurer;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.GigaSpace;

import java.io.Closeable;
import java.io.IOException;

public class RefTableCache implements Closeable {
    private final LocalViewSpaceConfigurer configurer;
    private final GigaSpace localViewSpace;

    public RefTableCache(GigaSpace masterSpace, Class<?> ... refTables){
        this.configurer = new LocalViewSpaceConfigurer(masterSpace.getSpace());
        for (Class<?> refTable: refTables){
            configurer.addViewQuery(new SQLQuery<>(refTable, ""));
        }
        IJSpace lvSpace = this.configurer.create();
        this.localViewSpace = new GigaSpaceConfigurer(lvSpace).create();
    }

    public RefTableCache(GigaSpace masterSpace, String[] types){
        this.configurer = new LocalViewSpaceConfigurer(masterSpace.getSpace());
        for (String type: types){
            configurer.addViewQuery(new SQLQuery<SpaceDocument>(type, ""));
        }

        IJSpace lvSpace = this.configurer.create();
        this.localViewSpace = new GigaSpaceConfigurer(lvSpace).create();
    }


    @Override
    public void close() throws IOException{
        configurer.close();
    }

    public GigaSpace getCachedSpace() {
        return  localViewSpace;
    }
}
