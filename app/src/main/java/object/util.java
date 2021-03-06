package object;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import object.dao.timeeventdao;
import object.dto.periodeventdto;
import object.dto.timeeventdto;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by BenX on 30/03/2017.
 */
public class util {
    public static void deleteDto(periodeventdto dto)
    {
        dto = new periodeventdto();
    }
    public static void deleteDto(timeeventdto dto)
    {
        dto = new timeeventdto();
    }

    public static List<Date> getSelectDate(periodeventdto dto)
    {
        List<Date> result = new ArrayList<>();
        List<Integer> listsday = getSelectDay(dto.dayselect);

        for (Date date = dto.datestart; date.before(addTime(dto.dateend, 1, define.DAY));
             date = addTime(date, 1, define.DAY))
        {
            int dow = date.getDay();
            if(listsday.contains(dow))
                result.add(date);
        }
        return result;
    }

    public static List<Integer> getSelectDay(String dayselect)
    {
        List<Integer> result = new ArrayList<>();
        for(int i=1;i<dayselect.length()+1;i++)
        {
            if(dayselect.charAt(i-1) == '1') {
                if(i == 7) result.add(0);
                else result.add(i);
            }
        }
        return result;
    }

    public static Date addTime(Date date, int number, int type)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        switch(type)
        {
            case define.YEAR:
            {
                c.add(Calendar.YEAR, number);
                break;
            }
            case define.MONTH:
            {
                c.add(Calendar.MONTH, number);
                break;
            }
            case define.DAY:
            {
                c.add(Calendar.DATE, number);
                break;
            }
            case define.HOUR:
            {
                c.add(Calendar.HOUR_OF_DAY, number);
                break;
            }
            case define.MINUTE:
            {
                c.add(Calendar.MINUTE, number);
                break;
            }
            case define.SECOND:
            {
                c.add(Calendar.SECOND, number);
                break;
            }
        }
        // convert calendar to date
        return c.getTime();
    }

    public static String numberDay(int i)
    {
        switch(i)
        {
            case 0: return "Hai";
            case 1: return "Ba";
            case 2: return "Tư";
            case 3: return "Năm";
            case 4: return "Sáu";
            case 5: return "Bảy";
            case 6: return "Chủ Nhật";
        }
        return "";
    }

    public static int dpToPx(int dp, DisplayMetrics metrics)
    {
        return Math.round(dp * (metrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static int pxToDp(int px)
    {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static String readDate(Date date)
    {
        int month = date.getMonth() + 1;
        int day = date.getDate();
        return String.format("%02d", day) + "/" + String.format("%02d", month);
    }

    public static String readTime(Date time)
    {
        int hour = time.getHours();
        int minute = time.getMinutes();
        return String.format("%02d", hour) + ":" + String.format("%02d", minute);
    }
}
