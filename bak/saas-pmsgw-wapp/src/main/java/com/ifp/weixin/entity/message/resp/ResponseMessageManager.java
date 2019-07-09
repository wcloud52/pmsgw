package com.ifp.weixin.entity.message.resp;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * 用户request请求消息管理类
 * 
 */
public class ResponseMessageManager {

	public static String responseMessageToXml(BaseMessage baseMessage) {
		xstream.alias("xml", baseMessage.getClass());
		if(baseMessage instanceof NewsMessage)
		xstream.alias("item", new Article().getClass());
		return xstream.toXML(baseMessage);
	}
	
	/**
	 * 扩展xstream，使其支持CDATA块
	 */
	private static XStream xstream = new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				// 对所有xml节点的转换都增加CDATA标记
				boolean cdata = true;
	
                public void startNode(String name, Class clazz) {  
                    super.startNode(name, clazz);  
                }  
				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
		}
	});
	
	
	public static void main(String[] args) {
		NewsMessage text=new NewsMessage();
		List<Article> list=new ArrayList<Article>();
		list.add(new Article());
		text.setArticles(list);
		text.setMsgType("text");
		String g=ResponseMessageManager.responseMessageToXml(text);
		System.out.println(g);
	}
}
