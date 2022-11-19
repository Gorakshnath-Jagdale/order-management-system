package com.oms.service.util;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Data
public final class Constants {
   public static class POStatus{
       public static final String ACTIVE_PO="ACTIVE";
       public static final String COMPLETED_PO="COMPLETED";
       public static final String AMENDED_PO="AMENDED";
       public static final String CANCEL_PO="CANCEL";
       public static final String ELEKTRONIKA_FEEDBACK="ELEKTRONIKA FEEDBACK";
public static final String UPLOAD_FOLDER="C:/Users/admin/Downloads/Software/Documents";
       public static List<String> getStatusList(int index){
           var list=new ArrayList<String>();
           switch (index)
           {
               case 1:
                   list.add(ACTIVE_PO);//1
                   break;
               case 2:
                   list.add(COMPLETED_PO);//2
                   break;
               case 3:
                   list.add(CANCEL_PO);//3
                   break;
               case 4://Only Active and Completed
                   list.add(ACTIVE_PO);
                   list.add(COMPLETED_PO);
                   break;
               case 0://ALL Nothing to skip
                   list.add(ACTIVE_PO);
                   list.add(COMPLETED_PO);
                   list.add(CANCEL_PO);
                   break;
           }
           return list;
       }

   }
}
