package com.example.asykur.kmus.Database;

import android.provider.BaseColumns;

public class DBContract
{
    static String TABLE_EN_IDN = "table_kamus_en_idn";
    static String TABLE_IDN_EN = "table_kamus_idn_en";

    static final class KamusColumns implements BaseColumns{
        static String KATA = "kata";
        static String ARTI = "arti";
    }
}
