var date = new Date();
var day = date.getDate();
var month = date.getMonth();
var year = date.getYear();

if(year<=200)
{
    year = 1900;
}
months = new Array('January', 'February', 'March', 'April', 'May', 'June', 'Jully', 'August', 'September', 'October', 'November', 'December');
days_in_month = new Array(31,28,31,30,31,30,31,31,30,31,30,31);
if(year%4 == 0 && year!=1900)
    {
      days_in_month[1]=29;
    }
total = days_in_month[month];

var date_today = day+' '+months[month]+' '+year;

beg_j = date;
beg_j.setMonth(month);
beg_j.setDate(1);


if(beg_j.getDate()==2)
        {
                beg_j=setDate(0);
        }

beg_j = beg_j.getDay();

function calendar()
{
        document.write('<table class="cal_body" cellpadding="0" cellspacing="0" border="0" ><tr class="cal_week"><td>S</td><td>M</td><td>T</td><td>W</td><td>T</td><td>F</td><td>S</td></tr><tr>');
        week = 0;
        for(i=1;i<=beg_j;i++)
        {
                document.write('<td class="cal_date_n">'+(days_in_month[month-1]-beg_j+i)+'</td>');
                week++;
        }
        for(i=1;i<=total;i++)
        {
                if(week==0)
                {
                        document.write('<tr>');
                }
                if(day==i)
                {
                        document.write('<td class="cal_date_today">'+i+'</td>');
                }
                else
                {
                        document.write('<td class="cal_date">'+i+'</td>');
                }
                week++;
                if(week==7)
                {
                        document.write('</tr>');
                        week=0;
                }
        }
        for(i=1;week!=0;i++)
        {
                document.write('<td class="cal_date_n">'+i+'</td>');
                week++;
                if(week==7)
                {
                        document.write('</tr>');
                        week=0;
                }
        }
        document.write('</table>');
        return true;
}

 function displayDate()
                                        {
                                document.getElementById("cale").innerHTML=Date();
                                    }

function calendar2()
{
        document.getElementById("cal").innerHTML = '<table class="cal_body" cellpadding="0" cellspacing="0" border="0" ><tr class="cal_week"><td>S</td><td>M</td><td>T</td><td>W</td><td>T</td><td>F</td><td>S</td></tr><tr>';
        week = 0;
        for(i=1;i<=beg_j;i++)
        {
                document.getElementById("cal").innerHTML = '<td class="cal_date_n">'+(days_in_month[month-1]-beg_j+i)+'</td>';
                week++;
        }
        for(i=1;i<=total;i++)
        {
                if(week==0)
                {
                        document.getElementById("cal").innerHTML = '<tr>';
                }
                if(day==i)
                {
                        document.getElementById("cal").innerHTML = '<td class="cal_date_today">'+i+'</td>';
                }
                else
                {
                        document.getElementById("cal").innerHTML = '<td class="cal_date">'+i+'</td>';
                }
                week++;
                if(week==7)
                {
                        document.getElementById("cal").innerHTML = '</tr>';
                        week=0;
                }
        }
        for(i=1;week!=0;i++)
        {
                document.getElementById("cal").innerHTML = '<td class="cal_date_n">'+i+'</td>';
                week++;
                if(week==7)
                {
                        document.getElementById("cal").innerHTML = '</tr>';
                        week=0;
                }
        }
        document.getElementById("cal").innerHTML = '</table>';
        return true;
}