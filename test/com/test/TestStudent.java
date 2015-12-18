package com.test;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.SearchFactory;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.pojo.Students;
import com.util.HibernateSessionFactory;

public class TestStudent {
	private static Session session = null;
	private static Transaction transaction = null;

	@Before
	public void init() {
//		会话对象
		session =  HibernateSessionFactory.getSession();
//		用这个工具比较好，哪里出错，有提示，而其他的方法没有他好用
		
//		开启事务
		transaction=session.beginTransaction();	
	}

	@After
	public void destory() {
		transaction.commit();
		session.close();
	}

	@Test
	public void test() {
		System.out.println("11111");
	}

	@Test
	public void testSaveStudent() {
		
		Students students1 = new Students("习大大", "北京中南海");
		session.save(students1);
		Students students2 = new Students("小李习大大王", "中国人北京郊区中南海军的实力");
		session.save(students2);
		Students students3 = new Students("小张", "太原小店恒大绿洲");
		session.save(students3);
		Students students5 = new Students("小李", "大同广灵");
		session.save(students5);
		Students students6 = new Students("大王", "大同广灵");
		session.save(students6);
		Students students7 = new Students("小何", "山西代县新高乡韩街村");
		session.save(students7);
		
//		session每一次提交，都会在索引目录下增量新增索引文件
	}

	/**
	 * 查找索引，返回符合条件的文件
	 * 
	 * 每次搜索，索引文件都重命名了，不知道索引文件内容有没有变化
	 */
	@Test
	public void testSearchIndex() {
		FullTextSession fullTextSession = Search.getFullTextSession(session);
		try {
//			重建 更新索引
//			fullTextSession.createIndexer().startAndWait();
		} catch (Exception e) {
			e.printStackTrace();
		}

		SearchFactory sf = fullTextSession.getSearchFactory();
		QueryBuilder qb = sf.buildQueryBuilder().forEntity(Students.class).get();
		org.apache.lucene.search.Query luceneQuery = qb.keyword().onFields("username", "address").matching("习")
				.createQuery();
		Query hibQuery = (Query) fullTextSession.createFullTextQuery(luceneQuery);
		List list = hibQuery.list();
		for (Object obj : list) {
			Students student = (Students) obj;
			System.out.println(student.toString());
		}
	}
}
