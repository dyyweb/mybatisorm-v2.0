package org.dy.common.orm.mysql.mybatis;

import org.dy.common.orm.mysql.mybatis.base.BuildFactory;
import org.dy.common.orm.mysql.mybatis.bean.TableWapper;
import org.dy.common.orm.mysql.mybatis.build.*;
import org.dy.common.orm.mysql.mybatis.enums.OutPathKey;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dengyang
 */
public class Mybatisorm {

	private static final List<BuildFactory> BUILD_LIST = new ArrayList<BuildFactory>();

	public static final String PROJECT_PATH = "D:\\code_template";

	static {
		BUILD_LIST.add(new BuildDao());
		BUILD_LIST.add(new BuildDto());
		BUILD_LIST.add(new BuildXml());
		BUILD_LIST.add(new BuildBean());
		BUILD_LIST.add(new BuildService());
		BUILD_LIST.add(new BuildServiceImpl());
	}

	public static void main(String[] args) throws Exception {
		List<TableWapper> tables = getTables();
		for (TableWapper t : tables) {
			for (BuildFactory b : BUILD_LIST) {
				b.buildTable(t);
			}
		}
		new BuildBase().buildTable();
	}

	private static List<TableWapper> getTables() throws Exception {
		TablesBuilder builder = new TablesBuilder();
		Map<OutPathKey,String> outPathMap = new HashMap<OutPathKey,String>();
		builder.setJdbcClass("com.mysql.jdbc.Driver");//驱动
		builder.setUrl("jdbc:mysql://192.168.0.27:3306/pets?useUnicode=true&amp;allowMultiQueries=true&amp;characterEncoding=UTF-8");//数据库链接
		builder.setName("root");//数据库用户名
		builder.setPwd("petspp.com");//数据库密码
		TablesBuilder.BASEPACKAGE = "com.pets.framework.base";//base包地址
		builder.setPojoPackage("com.pets.framework.domain");//pojo包地址
		builder.setDtoPackage("com.pets.framework.dto");//pojo包地址
		builder.setDaoPackage("com.pets.framework.dao");//dao包地址
		builder.setServicePackage("com.pets.framework.api");
		builder.setServiceImplPackage("com.pets.framework.service");
		File path_file = new File(PROJECT_PATH);
		if (!path_file.isDirectory()){
			path_file.mkdir();
			File base = new File(path_file, "base");
			base.mkdir();
			File model = new File(path_file, "domain");
			model.mkdir();
			File dto = new File(path_file, "dto");
			dto.mkdir();
			File dao = new File(path_file, "dao");
			dao.mkdir();
			File mapper = new File(path_file, "mapper");
			mapper.mkdir();
			File service = new File(path_file, "api");
			service.mkdir();
			File impl = new File(path_file, "service");
			impl.mkdir();
		}
		outPathMap.put(OutPathKey.DEFULT,PROJECT_PATH);
		outPathMap.put(OutPathKey.BASE,PROJECT_PATH + "\\base\\");
		outPathMap.put(OutPathKey.DO,PROJECT_PATH + "\\domain\\");
		outPathMap.put(OutPathKey.DTO,PROJECT_PATH + "\\dto\\");
		outPathMap.put(OutPathKey.DAO,PROJECT_PATH + "\\dao\\");
		outPathMap.put(OutPathKey.XML,PROJECT_PATH + "\\mapper\\");
		outPathMap.put(OutPathKey.SERVICE,PROJECT_PATH + "\\api\\");
		outPathMap.put(OutPathKey.SERVICE_IMPL,PROJECT_PATH + "\\service\\");
		builder.setOutPathMap(outPathMap);//生成文件地址
		builder.setTableName("%");//数据库表名 %全部
		return builder.build();
	}

}
