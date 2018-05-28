package com.rd.strivos.tandem;

import android.provider.BaseColumns;

/**
 * Created by COBURG DESIGN on 22-01-2016.
 */
public class PaymentsDBFields {

    public static abstract class PaymentsAdd implements BaseColumns {
        public static final String TABLE_NAME = "tblPayment";
        public static final String COLUMN_NAME_VENDOR_NAME = "vendor";
        public static final String COLUMN_NAME_REF_NO = "refno";
        public static final String COLUMN_NAME_REF_DATE = "refdate";
        public static final String COLUMN_NAME_AMOUNT = "amount";
        public static final String COLUMN_NAME_RECD_AMOUNT = "recedamount";
        public static final String COLUMN_NAME_REF_DOC_NO = "refdocno";
        public static final String COLUMN_NAME_REF_DOC_DATE = "refdocdate";
        public static final String COLUMN_NAME_PROJECT_TYPE = "projectype";
        public static final String COLUMN_NAME_PROJECT_NAME = "projectname";
        public static final String COLUMN_NAME_STATUS = "status";
    }
}
