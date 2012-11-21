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
 * Class:		RtfPullParserTest
 * Description:	Proyecto de Test para NRtfTree
 * ******************************************************************************/

package net.sgoliver.jrtftree.test;

import java.io.FileNotFoundException;
import java.io.IOException;

import junit.framework.Assert;
import static org.junit.Assert.*;
import org.junit.Test;
import net.sgoliver.jrtftree.core.RtfPullParser;
import net.sgoliver.jrtftree.core.RtfTree;

public class RtfPullParserTest //In Sync 
{
	@Test
    public void parseSimpleDocument()
    {
        RtfPullParser parser = new RtfPullParser();
        
        try {
			parser.loadRtfFile("test\\testdocs\\testdoc1.rtf");
		} catch (FileNotFoundException e) {
			;
		} catch (IOException e) {
			;
		}

        parserTests(parser);
    }

	@Test
    public void parseSimpleRtfText()
    {
        RtfTree tree = new RtfTree();
        tree.loadRtfFile("test\\testdocs\\testdoc1.rtf");

        RtfPullParser parser = new RtfPullParser();
        
        try {
			parser.loadRtfText(tree.getRtf());
		} catch (IOException e) {
			;
		}

        parserTests(parser);
    }

    private static void parserTests(RtfPullParser parser)
    {
    	try
    	{
	        int eventType = parser.getEventType();
	        assertEquals(RtfPullParser.START_DOCUMENT, eventType);
	
	        eventType = parser.next();
	        assertEquals(RtfPullParser.START_GROUP, eventType);
	
	        eventType = parser.next();
	        assertEquals(RtfPullParser.KEYWORD, eventType);
	        assertEquals("rtf", parser.getName());
	        assertEquals(true, parser.getHasParam());
	        assertEquals(1, parser.getParam());
	
	        eventType = parser.next();
	        assertEquals(RtfPullParser.KEYWORD, eventType);
	        assertEquals("ansi", parser.getName());
	        assertEquals(false, parser.getHasParam());
	
	        for (int i = 0; i < 3; i++)
	            parser.next();
	
	        eventType = parser.next();
	        assertEquals(RtfPullParser.START_GROUP, eventType);
	
	        for (int i = 0; i < 6; i++)
	            parser.next();
	
	        eventType = parser.next();
	        assertEquals(RtfPullParser.TEXT, eventType);
	        assertEquals("Times New Roman;", parser.getText());
	
	        eventType = parser.next();
	        assertEquals(RtfPullParser.END_GROUP, eventType);
	
	        for (int i = 0; i < 27; i++)
	            parser.next();
	
	        eventType = parser.next();
	        assertEquals(RtfPullParser.CONTROL, eventType);
	        assertEquals("*", parser.getName());
	        assertEquals(false, parser.getHasParam());
	
	        for (int i = 0; i < 40; i++)
	            parser.next();
	
	        eventType = parser.next();
	        assertEquals(RtfPullParser.CONTROL, eventType);
	        assertEquals("'", parser.getName());
	        assertEquals(true, parser.getHasParam());
	        assertEquals(233, parser.getParam());
	
	        eventType = parser.next();
	        assertEquals(RtfPullParser.TEXT, eventType);
	        assertEquals("st1 a", parser.getText());
	
	        eventType = parser.next();
	        assertEquals(RtfPullParser.CONTROL, eventType);
	        assertEquals("'", parser.getName());
	        assertEquals(true, parser.getHasParam());
	        assertEquals(241, parser.getParam());
	
	        eventType = parser.next();
	        assertEquals(RtfPullParser.TEXT, eventType);
	        assertEquals("u ", parser.getText());
	
	        eventType = parser.next();
	        assertEquals(RtfPullParser.TEXT, eventType);
	        assertEquals("{", parser.getText());
	
	        eventType = parser.next();
	        assertEquals(RtfPullParser.TEXT, eventType);
	        assertEquals("\\", parser.getText());
	
	        eventType = parser.next();
	        assertEquals(RtfPullParser.TEXT, eventType);
	        assertEquals("test2", parser.getText());
	
	        eventType = parser.next();
	        assertEquals(RtfPullParser.TEXT, eventType);
	        assertEquals("}", parser.getText());
	
	        for (int i = 0; i < 29; i++)
	            parser.next();
	
	        eventType = parser.next();
	        assertEquals(RtfPullParser.END_GROUP, eventType);
	
	        eventType = parser.next();
	        assertEquals(RtfPullParser.END_DOCUMENT, eventType);
    	}
    	catch(IOException ex)
    	{
    		Assert.fail();
    	}
    }
}
