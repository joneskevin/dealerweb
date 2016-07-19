package com.ava.util;

public class BbsCodeFilter {
	
	static String bbsCode[][] = {
        {
            "[b]", "<b>"
        }, {
            "[/b]", "</b>"
        }, {
            "[i]", "<i>"
        }, {
            "[/i]", "</i>"
        }, {
            "[u]", "<u>"
        }, {
            "[/u]", "</u>"
        }, {
            "[s]", "<s>"
        }, {
            "[/s]", "</s>"
        }, {
            "[hr]", "<hr>"
        }, {
            "[br]", "<br>"
        }, {
            "[p]", "<p>"
        }, {
            "[h1]", "<h1>"
        }, {
            "[/h1]", "</h1>"
        }, {
            "[h2]", "<h2>"
        }, {
            "[/h2]", "</h2>"
        }, {
            "[h3]", "<h3>"
        }, {
            "[/h3]", "</h3>"
        }, {
            "[h4]", "<h4>"
        }, {
            "[/h4]", "</h4>"
        }, {
            "[h5]", "<h5>"
        }, {
            "[/h5]", "</h5>"
        }, {
            "[h6]", "<h6>"
        }, {
            "[/h6]", "</h6>"
        }, {
            "[/size]", "</font>"
        }, {
            "[size=+1]", "<font size=\"+1\">"
        }, {
            "[size=+2]", "<font size=\"+2\">"
        }, {
            "[size=+3]", "<font size=\"+3\">"
        }, {
            "[size=+4]", "<font size=\"+4\">"
        }, {
            "[size=+5]", "<font size=\"+5\">"
        }, {
            "[size=+6]", "<font size=\"+6\">"
        }, {
            "[size=1]", "<font size=\"1\">"
        }, {
            "[size=2]", "<font size=\"2\">"
        }, {
            "[size=3]", "<font size=\"3\">"
        }, {
            "[size=4]", "<font size=\"4\">"
        }, {
            "[size=5]", "<font size=\"5\">"
        }, {
            "[size=6]", "<font size=\"6\">"
        }, {
            "[size=-1]", "<font size=\"-1\">"
        }, {
            "[size=-2]", "<font size=\"-2\">"
        }, {
            "[size=-3]", "<font size=\"-3\">"
        }, {
            "[size=-4]", "<font size=\"-4\">"
        }, {
            "[size=-5]", "<font size=\"-5\">"
        }, {
            "[size=-6]", "<font size=\"-6\">"
        }, {
            "[/font]", "</font>"
        }, {
            "[font=arial]", "<font face=\"arial\">"
        }, {
            "[font=times new roman]", "<font face=\"times new roman\">"
        }, {
            "[font=courier new]", "<font face=\"courier new\">"
        }, {
            "[font=century gothic]", "<font face=\"Century Gothic\">"
        }, {
            "[/color]", "</span>"
        }, {
            "[color=skyblue]", "<span style=\"color: skyblue\">"
        }, {
            "[color=royalblue]", "<span style=\"color: royalblue\">"
        }, {
            "[color=blue]", "<span style=\"color: blue\">"
        }, {
            "[color=darkblue]", "<span style=\"color: darkblue\">"
        }, {
            "[color=orange]", "<span style=\"color: orange\">"
        }, {
            "[color=orangered]", "<span style=\"color: orangered\">"
        }, {
            "[color=crimson]", "<span style=\"color: crimson\">"
        }, {
            "[color=red]", "<span style=\"color: red\">"
        }, {
            "[color=firebrick]", "<span style=\"color: firebrick\">"
        }, {
            "[color=darkred]", "<span style=\"color: darkred\">"
        }, {
            "[color=green]", "<span style=\"color: green\">"
        }, {
            "[color=limegreen]", "<span style=\"color: limegreen\">"
        }, {
            "[color=seagreen]", "<span style=\"color: seagreen\">"
        }, {
            "[color=deeppink]", "<span style=\"color: deeppink\">"
        }, {
            "[color=tomato]", "<span style=\"color: tomato\">"
        }, {
            "[color=coral]", "<span style=\"color: coral\">"
        }, {
            "[color=purple]", "<span style=\"color: purple\">"
        }, {
            "[color=indigo]", "<span style=\"color: indigo\">"
        }, {
            "[color=burlywood]", "<span style=\"color: burlywood\">"
        }, {
            "[color=sandybrown]", "<span style=\"color: sandybrown\">"
        }, {
            "[color=sienna]", "<span style=\"color: sienna\">"
        }, {
            "[color=chocolate]", "<span style=\"color: chocolate\">"
        }, {
            "[color=teal]", "<span style=\"color: teal\">"
        }, {
            "[color=silver]", "<span style=\"color: silver\">"
        }, {
            "[color=brown]", "<span style=\"color: brown\">"
        }, {
            "[color=yellow]", "<span style=\"color: yellow\">"
        }, {
            "[color=olive]", "<span style=\"color: olive\">"
        }, {
            "[color=cyan]", "<span style=\"color: cyan\">"
        }, {
            "[color=violet]", "<span style=\"color: violet\">"
        }, {
            "[color=white]", "<span style=\"color: white\">"
        }, {
            "[color=black]", "<span style=\"color: black\">"
        }, {
            "[color=pink]", "<span style=\"color: pink\">"
        }, {
            "[color=purple]", "<span style=\"color: purple\">"
        }, {
            "[color=navy]", "<span style=\"color: navy\">"
        }, {
            "[color=beige]", "<span style=\"color: beige\">"
        }, {
            "[list]", "<ul>"
        }, {
            "[/list]", "</ul>"
        }, {
            "[list=1]", "<ul type=\"1\">"
        }, {
            "[/list=1]", "</ul>"
        }, {
            "[list=a]", "<ul type=\"a\">"
        }, {
            "[/list=a]", "</ul>"
        }, {
            "[list=A]", "<ul type=\"A\">"
        }, {
            "[/list=A]", "</ul>"
        }, {
            "[list=i]", "<ul type=\"i\">"
        }, {
            "[/list=i]", "</ul>"
        }, {
            "[list=I]", "<ul type=\"I\">"
        }, {
            "[/list=I]", "</ul>"
        }, {
            "[*]", "<li>"
        }, {
            "[code]", "<pre>"
        }, {
            "[/code]", "</pre>"
        }, {
            "[quote]", "<table width='96%' cellspacing='1' cellpadding='3' border='0' align='center'><tr><td>&nbsp;</td></tr><tr><td class='quote'>"
        }, {
            "[/quote]", "</td></tr></table>"
        }
    };
	
	/**	处理字符串内容，把论坛一些特定符号转换成html标签
	 * @ 比如把"[b]"转换成"<b>"
	 *  */
	public static String enableBbsCodeFilter(String input) {
		if (input==null){
			return "";
		}

        int beginIndex = 0;
        int currentBracketIndex = 0;
        int inputLength = input.length();
        int bbsCodeLength = bbsCode.length;
        StringBuffer output = new StringBuffer(inputLength * 2);
        while(beginIndex < inputLength) 
        {
            currentBracketIndex = input.indexOf(91, beginIndex);
            String remain;
            if(currentBracketIndex == -1)
            {
                remain = input.substring(beginIndex, inputLength);
                output.append(remain);
                break;
            }
            remain = input.substring(beginIndex, currentBracketIndex);
            output.append(remain);
            boolean matchFound = false;
            for(int i = 0; i < bbsCodeLength; i++)
            {
                String currentEmotion = bbsCode[i][0];
                int endIndex = currentBracketIndex + currentEmotion.length();
                if(endIndex > inputLength)
                    continue;
                String match = input.substring(currentBracketIndex, endIndex);
                if(!currentEmotion.equals(match))
                    continue;
                output.append(bbsCode[i][1]);
                beginIndex = currentBracketIndex + currentEmotion.length();
                matchFound = true;
                break;
            }

            if(!matchFound)
            {
                beginIndex = currentBracketIndex + 1;
                output.append('[');
            }
        }
        return output.toString();
	}

	/**	处理字符串内容，把论坛一些涉及URL的特定符号转换成html标签
	 * @ 图片：[img]http://www.xxx.com/xxx.jpg[/img] ==> <img src='http://www.xxx.com/xxx.jpg' border='0'>
	 * @ 超链接：[url=http://www.xxx.com]公司[/url] ==> <a href='http://www.xxx.com' target="_blank">公司</a>
	 * @ 发邮件：[url=mailto:xxx@kingsh.com]给我发邮件[/url]  ==> <a href="mailto:xxx@kingsh.com" target="_blank">给我发邮件</a>
	 *  */
    public static String enableUrlFilter(String input)
    {
        if(input == null || input.length() == 0)
            return input;
        StringBuffer buf = new StringBuffer(input.length() + 25);
        char chars[] = input.toCharArray();
        int len = input.length();
        int index = -1;
        int i = 0;
        int j = 0;
        int oldend = 0;
        while(++index < len) 
        {
            char cur = chars[i = index];
            j = -1;
            if((cur == 'f' && index < len - 6 && chars[++i] == 't' && chars[++i] == 'p' || cur == 'h' && (i = index) < len - 7 && chars[++i] == 't' && chars[++i] == 't' && chars[++i] == 'p' && (chars[++i] == 's' || chars[--i] == 'p')) && i < len - 4 && chars[++i] == ':' && chars[++i] == '/' && chars[++i] == '/')
                j = ++i;
            if(j > 0)
            {
                if(index == 0 || (cur = chars[index - 1]) != '\'' && cur != '"' && cur != '<' && cur != '=')
                {
                    cur = chars[j];
                    while(j < len) 
                    {
                        if(cur == ' ' || cur == '\t' || cur == '\'' || cur == '"' || cur == '<' || cur == '[' || cur == '\n' || cur == '\r' && j < len - 1 && chars[j + 1] == '\n')
                            break;
                        if(++j < len)
                            cur = chars[j];
                    }
                    cur = chars[j - 1];
                    if(cur == '.' || cur == ',' || cur == ')' || cur == ']')
                        j--;
                    buf.append(chars, oldend, index - oldend);
                    buf.append("<a href=\"");
                    String href = input.substring(index, j).trim();
                    buf.append(href);
                    buf.append('"');
                    buf.append(" target=\"_blank\"");
                    buf.append('>');
                    buf.append(href);
                    buf.append("</a>");
                } else
                {
                    buf.append(chars, oldend, j - oldend);
                }
                oldend = index = j;
            } else
            if(cur == '[' && index < len - 6 && chars[i = index + 1] == 'u' && chars[++i] == 'r' && chars[++i] == 'l' && (chars[++i] == '=' || chars[i] == ' '))
            {
                j = ++i;
                int u2;
                int u1 = u2 = input.indexOf("]", j);
                if(u1 > 0)
                    u2 = input.indexOf("[/url]", u1 + 1);
                if(u2 < 0)
                {
                    buf.append(chars, oldend, j - oldend);
                    oldend = j;
                } else
                {
                    buf.append(chars, oldend, index - oldend);
                    buf.append("<a href=\"");
                    String href = input.substring(j, u1).trim();
                    if(href.indexOf("://") == -1 && !href.startsWith("mailto:"))
                        href = "http://" + href;
                    if(href.indexOf("javascript:") == -1 && href.indexOf("file:") == -1)
                        buf.append(href);
                    buf.append("\" target=\"_blank");
                    buf.append("\">");
                    buf.append(input.substring(u1 + 1, u2).trim());
                    buf.append("</a>");
                    oldend = u2 + 6;
                }
                index = oldend - 1;
            } else
            if(cur == '[' && index < len - 6 && chars[i = index + 1] == 'i' && chars[++i] == 'm' && chars[++i] == 'g' && chars[++i] == ']')
            {
                j = ++i;
                int u1 = j - 1;
                int u2 = input.indexOf("[/img]", u1 + 1);
                if(u2 < 0)
                {
                    buf.append(chars, oldend, j - oldend);
                    oldend = j;
                } else
                {
                    buf.append(chars, oldend, index - oldend);
                    buf.append("<img src=\"");
                    String href = input.substring(u1 + 1, u2).trim();
                    if(href.indexOf("://") == -1)
                        href = "http://" + href;
                    if(href.indexOf("javascript:") == -1 && href.indexOf("file:") == -1)
                        buf.append(href);
                    buf.append("\" border=\"0\">");
                    oldend = u2 + 6;
                }
                index = oldend - 1;
            }
        }
        if(oldend < len)
            buf.append(chars, oldend, len - oldend);
        return buf.toString();
    }
    
}
