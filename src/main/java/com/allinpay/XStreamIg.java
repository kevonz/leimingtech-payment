package com.allinpay;


import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
import com.thoughtworks.xstream.mapper.MapperWrapper;


public class XStreamIg extends XStream
{
	public XStreamIg()
	{
	}
    public XStreamIg(HierarchicalStreamDriver hierarchicalStreamDriver)
    {
        super(hierarchicalStreamDriver);
    }
    protected MapperWrapper wrapMapper(MapperWrapper next)
    {
        return new MapperWrapper(next)
        {
            public boolean shouldSerializeMember(Class definedIn, String fieldName)
            {
            	//System.out.println(fieldName);
                if (super.shouldSerializeMember(definedIn, fieldName))
                { 
                    Object field = null;
                    while (definedIn != Object.class)
                    {
                        try
                        {
                            field = definedIn.getDeclaredField(fieldName);
                            break;
                        }
                        catch (NoSuchFieldException e)
                        {
                            definedIn = definedIn.getSuperclass();
                        }
                        catch (Exception e)
                        {
                        	break;
                        }
                    }
                    if(field==null)
                    {
                    	try
                    	{
                    		field=super.realClass(fieldName);
                    	}
                    	catch(Exception e)
                    	{
                    		
                    	}
                    }
                    return field != null;
                } 
                return false;
            } 
        }; 
    } 
	public static String toXml(Object o)
	{
		return xs.toXML(o);
	}
	public static Object fromXml(String xml)
	{
		return xs.fromXML(xml);
	}
	public static XStreamIg xs =new XStreamIg();
}
