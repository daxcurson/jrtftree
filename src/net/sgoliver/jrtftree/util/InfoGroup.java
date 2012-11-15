/********************************************************************************
 *   This file is part of NRtfTree Library.
 *
 *   JRtfTree Library is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published by
 *   the Free Software Foundation; either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   JRtfTree Library is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with this program. If not, see <http://www.gnu.org/licenses/>.
 ********************************************************************************/

/********************************************************************************
 * Library:		JRtfTree
 * Version:     v0.3
 * Date:		15/11/2012
 * Copyright:   2006-2012 Salvador Gomez
 * Home Page:	http://www.sgoliver.net
 * GitHub:		https://github.com/sgolivernet/jrtftree
 * Class:		InfoGroup
 * Description:	Representa un documento RTF en forma de árbol.
 * ******************************************************************************/

package net.sgoliver.jrtftree.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class InfoGroup //In Sync
{
    private String title = "";
    private String subject = "";
    private String author = "";
    private String manager = "";
    private String company = "";
    private String operator = "";
    private String category = "";
    private String keywords = "";
    private String comment = "";
    private String doccomm = "";
    private String hlinkbase = "";
    private Calendar creatim = null;
    private Calendar revtim = null;
    private Calendar printim = null;
    private Calendar buptim = null;
    private int version = -1;
    private int vern = -1;
    private int edmins = -1;
    private int nofpages = -1;
    private int nofwords = -1;
    private int nofchars = -1;
    private int id = -1;
    
	public void setTitle(String title)
	{
		this.title = title;
	}
	public String getTitle()
	{
		return title;
	}
	public void setSubject(String subject)
	{
		this.subject = subject;
	}
	public String getSubject()
	{
		return subject;
	}
	public void setAuthor(String author)
	{
		this.author = author;
	}
	public String getAuthor()
	{
		return author;
	}
	public void setManager(String manager)
	{
		this.manager = manager;
	}
	public String getManager()
	{
		return manager;
	}
	public void setCompany(String company)
	{
		this.company = company;
	}
	public String getCompany()
	{
		return company;
	}
	public void setOperator(String operator)
	{
		this.operator = operator;
	}
	public String getOperator()
	{
		return operator;
	}
	public void setCategory(String category)
	{
		this.category = category;
	}
	public String getCategory()
	{
		return category;
	}
	public void setKeywords(String keywords)
	{
		this.keywords = keywords;
	}
	public String getKeywords()
	{
		return keywords;
	}
	public void setComment(String comment)
	{
		this.comment = comment;
	}
	public String getComment()
	{
		return comment;
	}
	public void setDoccomm(String doccomm)
	{
		this.doccomm = doccomm;
	}
	public String getDoccomm()
	{
		return doccomm;
	}
	public void setHlinkbase(String hlinkbase)
	{
		this.hlinkbase = hlinkbase;
	}
	public String getHlinkbase()
	{
		return hlinkbase;
	}
	public void setCreationTime(Calendar creatim)
	{
		this.creatim = creatim;
	}
	public Calendar getCreationTime()
	{
		return creatim;
	}
	public void setRevisionTime(Calendar revtim)
	{
		this.revtim = revtim;
	}
	public Calendar getRevisionTime()
	{
		return revtim;
	}
	public void setLastPrintTime(Calendar printim)
	{
		this.printim = printim;
	}
	public Calendar getLastPrintTime()
	{
		return printim;
	}
	public void setBackupTime(Calendar buptim)
	{
		this.buptim = buptim;
	}
	public Calendar getBackupTime()
	{
		return buptim;
	}
	public void setVersion(int version)
	{
		this.version = version;
	}
	public int getVersion()
	{
		return version;
	}
	public void setInternalVersion(int vern)
	{
		this.vern = vern;
	}
	public int getInternalVersion()
	{
		return vern;
	}
	public void setEditingTime(int edmins)
	{
		this.edmins = edmins;
	}
	public int getEditingTime()
	{
		return edmins;
	}
	public void setNofpages(int nofpages)
	{
		this.nofpages = nofpages;
	}
	public int getNofpages()
	{
		return nofpages;
	}
	public void setNofwords(int nofwords)
	{
		this.nofwords = nofwords;
	}
	public int getNofwords()
	{
		return nofwords;
	}
	public void setNofchars(int nofchars)
	{
		this.nofchars = nofchars;
	}
	public int getNofchars()
	{
		return nofchars;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public int getId()
	{
		return id;
	}
	
	@Override
	public String toString()
    {
		String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		
        StringBuilder str = new StringBuilder();
        
        String NL = System.getProperty("line.separator");

        str.append("Title     : " + this.title + NL);
        str.append("Subject   : " + this.subject + NL);
        str.append("Author    : " + this.author + NL);
        str.append("Manager   : " + this.manager + NL);
        str.append("Company   : " + this.company + NL);
        str.append("Operator  : " + this.operator + NL);
        str.append("Category  : " + this.category + NL);
        str.append("Keywords  : " + this.keywords + NL);
        str.append("Comment   : " + this.comment + NL);
        str.append("DComment  : " + this.doccomm + NL);
        str.append("HLinkBase : " + this.hlinkbase + NL);
        str.append("Created   : " + (this.creatim == null ? "null" : sdf.format(this.creatim.getTime())) + NL);
        str.append("Revised   : " + (this.revtim == null ? "null" : sdf.format(this.revtim.getTime())) + NL);
        str.append("Printed   : " + (this.printim == null ? "null" : sdf.format(this.printim.getTime())) + NL);
        str.append("Backup    : " + (this.buptim == null ? "null" : sdf.format(this.buptim.getTime())) + NL);
        str.append("Version   : " + this.version + NL);
        str.append("IVersion  : " + this.vern + NL);
        str.append("Editing   : " + this.edmins + NL);
        str.append("Num Pages : " + this.nofpages + NL);
        str.append("Num Words : " + this.nofwords + NL);
        str.append("Num Chars : " + this.nofchars + NL);
        str.append("Id        : " + this.id + NL);

        return str.toString();
    }
}
