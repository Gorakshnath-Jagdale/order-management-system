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

       public static List<String> getStatusList(int index){
           var list=new ArrayList<String>();
           switch (index)
           {
               case 1:
                   list.add(ACTIVE_PO);
                   break;
               case 2:
                   list.add(COMPLETED_PO);
                   break;
               case 3:
                   list.add(AMENDED_PO);
                   break;
               case 4:
                   list.add(CANCEL_PO);
                   break;
               case 5://ONly Active and Completed
                   list.add(ACTIVE_PO);
                   list.add(COMPLETED_PO);
                   break;
               case 6://ALL Nothing to skip
                   list.add(ACTIVE_PO);
                   list.add(COMPLETED_PO);
                   list.add(AMENDED_PO);
                   list.add(CANCEL_PO);
                   break;
           }
           return list;
       }

   }
}
