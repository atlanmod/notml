package fr.imta.naomod;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.compilers.ThingMLCompiler;
import org.thingml.compilers.c.posix.PosixCompiler;
import org.thingml.networkplugins.c.posix.PosixJSONSerializerPlugin;
import org.thingml.networkplugins.c.posix.PosixMQTTPlugin;
import org.thingml.xtext.ThingMLStandaloneSetup;
import org.thingml.xtext.thingML.ThingMLModel;

import clang.ClangCompiler;
import framework.IoTCompiler;
import javalang.JavalangCompiler;
import lang.IotlangStandaloneSetup;
import lang.iotlang.Bind;
import lang.iotlang.InstanceThing;
import lang.iotlang.IoTLangModel;
import lang.iotlang.NetworkConfiguration;
import lang.iotlang.Topic;


public class EntryPoint {
	public static void main(String[] args) throws IOException {
		procedure();
	}

	private static void procedure() {
		File input = null;
		input = new File("testing.iotlang");
		
		registerFactory();
		ResourceSet rs = new ResourceSetImpl();
        URI xmiuri = URI.createFileURI(input.getAbsolutePath());
        Resource model = rs.createResource(xmiuri);
        try {
			model.load(null);
	        EcoreUtil.resolveAll(model);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        for (Resource r : model.getResourceSet().getResources()) {
            checkEMFErrorsAndWarnings(r, Logger.SYSTEM);
        }
        
        IoTLangModel iotModel = (IoTLangModel) model.getContents().get(0);
        for (lang.iotlang.Thing th : iotModel.getThings()) {
        	String code = th.getCode().get(0);
            code = code.replace("#", "");
            //System.out.println(""+code);
            
            ThingMLModel thgmodel = loadModel(code);
            System.out.println(thgmodel.getTypes().get(0).getName());
            //System.out.println(((Thing) thgmodel.getTypes().get(0)).getPorts().get(0).getName());
            
//            System.out.println(thgmodel.getTypes().get(0).getName());
//            System.out.println(thgmodel.getTypes().get(0).getName());
            
            ThingMLCompiler thgCompile = new PosixCompiler();
            
            PosixMQTTPlugin mqtt = new PosixMQTTPlugin();
            PosixJSONSerializerPlugin jsonposix = new PosixJSONSerializerPlugin();
            thgCompile.addSerializationPlugin(jsonposix);
            thgCompile.addNetworkPlugin(mqtt);
            
            File folder = new File("/home/imad/dev/workspaces/iotlang/generators/output/"+thgmodel.getConfigs().get(0).getName());
            thgCompile.setOutputDirectory(folder);
            thgCompile.compile(thgmodel.getConfigs().get(0));
		}
        
        File outputFile = null;
        outputFile = new File("main.ino");
		BufferedWriter buffer=null;
        
		IoTCompiler compiler = new JavalangCompiler();
		IoTCompiler compilerC = new ClangCompiler();
		//System.out.println("Generation pour : " + compiler.getID());
        //System.out.println("Generation pour : " + compilerC.getID());
		//compiler.compile(iotModel,iotModel.getConfigs().get(0),Logger.SYSTEM );
		
		
		try {
			buffer = new BufferedWriter(new FileWriter(outputFile));
			for (InstanceThing instanceThing : iotModel.getNetworkConfigs().get(0).getThingInstances()) {
				buffer.write("InstanceThing : "+sign(iotModel.getNetworkConfigs().get(0), instanceThing)+"\n");
			}
			for (Bind instanceThing : iotModel.getNetworkConfigs().get(0).getBinds()) {
				buffer.write("Bind : "+iotModel.getNetworkConfigs().get(0).getBinds().size()+"\n");
			}
			buffer.write("Domain : "+iotModel.getNetworkConfigs().get(0).getDomain().get(0).getName().replace("\"", "")+"\n");
			/*for (Rule rule : iotModel.getConfigs().get(0).getInstancePoliciy().get(0).getTypePolicy().getHasRules()) {
				if(rule.getObject()!=null) {
					String objectName = rule.getObject().getPorts().get(0).getName();
					buffer.write("Rules : "+objectName+"\n");
				}
			}*/
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
	          if ( buffer != null ) {
	            try {
	            	buffer.close();
	            	
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	       }
	     }
        generateSQLRules(iotModel);
		generateTextRules(iotModel);
		
		GenerateCode generateCode = new GenerateCode();
		generateCode.replaceInFile("arduino_xbee_main.ino", "###TOPIC###","room1");
		generateCode.replaceInFile("arduino_xbee_main.ino", "###CLIENT_ID###","fr:imt:dapi:roomA246:140162625");		
	}
	 public static ThingMLModel loadModel(String str) {
		 registerThingMLFactory();
	        ResourceSet rs = new ResourceSetImpl();
	        Resource resource = rs.createResource(URI.createURI("dummy:/example.thingml"));
	        InputStream in = new ByteArrayInputStream(str.getBytes());
	        try {
				resource.load(in, rs.getLoadOptions());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				resource.getErrors();
			}
	        ThingMLModel model = (ThingMLModel) resource.getContents().get(0);
	        return model;
	    }
	
	public String getTemplateByID(String template_id) {
        final InputStream input = getClass().getClassLoader().getResourceAsStream(template_id);
        String result = null;
        try {
            if (input != null) {
                result = org.apache.commons.io.IOUtils.toString(input, java.nio.charset.Charset.forName("UTF-8"));
                input.close();
            } else {
                System.out.println("[Error] Template not found: " + template_id);
            }
        } catch (Exception e) {
            //e.printStackTrace();
            return null; // the template was not found
        }
        return result;
    }
	
	
	
	
    private static void registerFactory() {
    	IotlangStandaloneSetup.doSetup();
    }
    private static void registerThingMLFactory() {
    	ThingMLStandaloneSetup.doSetup();
    }
	private static boolean checkEMFErrorsAndWarnings(Resource model, Logger log) {
    	log.info("Checking for EMF errors and warnings");
        boolean isOK = true;
        if (model.getErrors().size() > 0) {
            isOK = false;
            log.error("ERROR: The input model contains " + model.getErrors().size() + " errors.");
            for (Resource.Diagnostic d : model.getErrors()) {    
            		String location = d.getLocation();
            		if (location == null) {
            			location = model.getURI().toFileString();
            		}
            		log.error("Error in file  " + location + " (" + d.getLine() + ", " + d.getColumn() + "): " + d.getMessage());
            }
        } else {
        	log.info("No error was found !");
        }

        if (model.getWarnings().size() > 0) {
        	log.warning("WARNING: The input model contains " + model.getWarnings().size() + " warnings.");
            for (Resource.Diagnostic d : model.getWarnings()) {
          		String location = d.getLocation();
        		if (location == null) {
        			location = model.getURI().toFileString();
        		}
        		log.warning("Warning in file  " + location + " (" + d.getLine() + ", " + d.getColumn() + "): " + d.getMessage());
            }
        }
        return isOK;
    }
	
	public static String sha256(String base) {
	    try{
	        MessageDigest digest = MessageDigest.getInstance("SHA-256");
	        byte[] hash = digest.digest(base.getBytes("UTF-8"));
	        StringBuffer hexString = new StringBuffer();

	        for (int i = 0; i < hash.length; i++) {
	            String hex = Integer.toHexString(0xff & hash[i]);
	            if(hex.length() == 1) hexString.append('0');
	            hexString.append(hex);
	        }

	        return hexString.toString();
	    } catch(Exception ex){
	       throw new RuntimeException(ex);
	    }
	}
	
	public static String sign(NetworkConfiguration config, InstanceThing thing) {
		String  signature = sha256(config.getDomain().get(0).getName()+thing.getName().toString());
		return String.valueOf((signature.hashCode() & 0xfffffff));
	}
	protected static int accessConverter(String direction) {
		switch(direction) {
		case "=>":
			return 2;
		case "<=>":
			return 2;
		case "<=":
			return 1;
		default:
			return 0;
		}
	}
	protected static String accessConverterTxt(String direction) {
		switch(direction) {
		case "=>":
			return "readwrite";
		case "<=>":
			return "readwrite";
		case "<=":
			return "read";
		default:
			return "";
		}
	}
	public static void generateTextRules(IoTLangModel iotModel) {
		File sqlFile = null;
		sqlFile = new File("acl.txt");
		BufferedWriter bufferSql =null;
		try {
			bufferSql = new BufferedWriter(new FileWriter(sqlFile));
			StringBuffer rulesSql = new StringBuffer();
			for (Bind bind : iotModel.getNetworkConfigs().get(0).getBinds()) {
				String signature = sign(iotModel.getNetworkConfigs().get(0),bind.getThingInstance());
				for (Topic topic : bind.getTopics()) {
					String topicName = topic.getName();
					String accessRight = accessConverterTxt(bind.getDirection());
					rulesSql.append(
							"user "+iotModel.getNetworkConfigs().get(0).getDomain().get(0).getName().replace("\"", "")+":"+signature+"\n" + 
							"topic "+accessRight+" "+topicName+"\n \n");
				}
				
			}
			bufferSql.write(""+rulesSql);
			bufferSql.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
	          if ( bufferSql != null ) {
	            try {
	            	bufferSql.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	       }
	     }
		
	}
	
	public static void generateSQLRules(IoTLangModel iotModel) {
		File sqlFile = null;
		sqlFile = new File("acl.sql");
		BufferedWriter bufferSql =null;
		try {
			bufferSql = new BufferedWriter(new FileWriter(sqlFile));
			StringBuffer rulesSql = new StringBuffer();
			for (Bind bind : iotModel.getNetworkConfigs().get(0).getBinds()) {
				String thingName = bind.getThingInstance().getName();
				String signature = sign(iotModel.getNetworkConfigs().get(0),bind.getThingInstance());
				String allID = iotModel.getNetworkConfigs().get(0).getDomain().get(0).getName().replace("\"", "")+":"+signature;
				System.out.println("ID for "+thingName+": " + allID);
				for (Topic topic : bind.getTopics()) {
					String topicName = topic.getName();
					int accessRight = accessConverter(bind.getDirection());
					rulesSql.append(
							"INSERT INTO acls (username,topic,rw)\n" + 
							"SELECT * FROM (SELECT "+allID+", '"+topicName+"', "+accessRight+") AS tmp\n" + 
							"WHERE NOT EXISTS (\n" + 
							"    SELECT username FROM acls WHERE username = "+allID+" AND topic='"+topicName+"' AND rw="+accessRight+"\n" + 
							") LIMIT 1;\n \n");
				}
				
			}
			bufferSql.write("CREATE TABLE  IF NOT EXISTS acls ( id INTEGER AUTO_INCREMENT, username VARCHAR(25) "
					+ "NOT NULL, topic VARCHAR(256) NOT NULL, rw INTEGER(1) "
					+ "NOT NULL DEFAULT 1, PRIMARY KEY (id) );\n \n" + 
					rulesSql);
			bufferSql.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
	          if ( bufferSql != null ) {
	            try {
	            	bufferSql.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	       }
	     }
		
	}
}
