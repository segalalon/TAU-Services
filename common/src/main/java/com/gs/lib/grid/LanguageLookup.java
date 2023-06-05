package com.gs.lib.grid;

import com.gigaspaces.document.SpaceDocument;
import com.gs.model.ICTBIX01_INDXLAK_Wrapper;
import com.gs.model.ICTBIX02_HESBONLAK_Wrapper;
import com.j_spaces.core.client.SQLQuery;
import org.openspaces.core.GigaSpace;

public class LanguageLookup {

    GigaSpace gigaSpace;
    private static String HESBONLAK_QUERY = ICTBIX02_HESBONLAK_Wrapper.IX02_MIS_SNF + " = ? AND " + ICTBIX02_HESBONLAK_Wrapper.IX02_MIS_CHEN + " = ?";
    private static String INDXLAK_QUERY = ICTBIX01_INDXLAK_Wrapper.IX01_MIS_LAK + " = ? AND " + ICTBIX01_INDXLAK_Wrapper.IX01_MIS_SNF + " = ?";

    public LanguageLookup(GigaSpace gigaSpace) {
        this.gigaSpace = gigaSpace;
    }

    public String getSafa(short snif, long chn) {

        SQLQuery<SpaceDocument> sqlQuery = new SQLQuery<SpaceDocument>(ICTBIX02_HESBONLAK_Wrapper.getTypeName(), HESBONLAK_QUERY)
                .setParameter(1, snif)
                .setParameter(2, (int)chn);

        SpaceDocument res = gigaSpace.read(sqlQuery);
        if (res == null) {
            return "";
        }

        ICTBIX02_HESBONLAK_Wrapper heshbon = new ICTBIX02_HESBONLAK_Wrapper(res);

        int misLak = heshbon.get_IX02_MIS_LAK();

        SQLQuery<SpaceDocument> sqlQuery2 = new SQLQuery<SpaceDocument>(ICTBIX01_INDXLAK_Wrapper.getTypeName(), INDXLAK_QUERY)
                .setParameter(1, misLak)
                .setParameter(2, snif);

        SpaceDocument res2 = gigaSpace.read(sqlQuery2);
        if (res2 == null) {
            return "";
        }
        ICTBIX01_INDXLAK_Wrapper indexLak = new ICTBIX01_INDXLAK_Wrapper(res2);

        return indexLak.get_IX01_SEM_SAFA();

    }
}
