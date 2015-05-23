package com.proyecto.gmwork.proyectoandroid.controller.utilidades;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Mateo on 22/05/2015.
 */
public class ConvertorJsonDinamico {


        public ConvertorJsonDinamico() {

        }

        public static List<Map<String, Object>> formatJsonInput(String rawJsonInput) {
            List<Map<String, Object>> listOfEntities = new ArrayList();
            Map<String, Object> mappedAttributes = null;
            try {
                JSONObject jsonResponse = new JSONObject(rawJsonInput);
                JSONArray array = jsonResponse.getJSONArray("value");
                for (int j = 0; j < array.length(); j++) {
                    JSONObject jsonChildNode = array.getJSONObject(j);
                    mappedAttributes = new TreeMap<>();
                    for (int k = 0; k < jsonChildNode.names().length(); k++) {
                        String key = jsonChildNode.names().getString(k);
                        Object value = jsonChildNode.get(key);
                    /*Aquest if no se si fara falta. El contingut del if, sÃ­*/
                        if (!key.contains("odata")) {
                            mappedAttributes.put(key, value);
                        }
                    /**/
                    }
                    listOfEntities.add(mappedAttributes);
                }
            } catch (Exception e) {
                System.out.println("ERROR IN formatJson... " + e.getMessage());
            }
            return listOfEntities;
        }

        public static List<Map<String, Object>> formatJsonInputWithOneEntry(String rawJsonInput) {
            List<Map<String, Object>> listOfEntities = new ArrayList();
            Map<String, Object> mappedAttributes = new TreeMap<>();
            try {
                JSONObject jsonResponse = new JSONObject(rawJsonInput);
                for (int k = 0; k < jsonResponse.names().length(); k++) {
                    String key = jsonResponse.names().getString(k);
                    Object value = jsonResponse.get(key);
                    /*Aquest if no se si fara falta. El contingut del if, sÃ­*/
                    if (!key.contains("odata")) {
                        mappedAttributes.put(key, value);
                    }
                    /**/
                }
                listOfEntities.add(mappedAttributes);
            } catch (Exception e) {
                System.out.println("ERROR IN formatJson... " + e.getMessage());
            }
            return listOfEntities;
        }

        //Converteix una llista d'objectes en forma d'un map en una llista d'objectes d'una classe determinada
        public static <T> List<T> getObjectsFromFormattedJson(Class<T> objectClass, List<Map<String, Object>> listOfFormattedObjects, Activity activity) {
            List<T> listOfEntities = new ArrayList();
            Field[] fieldsOfEntity = objectClass.getDeclaredFields();
            Method[] methodsOfEntity = objectClass.getDeclaredMethods();
            T entity = null;
            Map<String, Object> entityInMapFormat = null;
            Iterator<String> mapIterator = null;

            try {
                for (int i = 0; i < listOfFormattedObjects.size(); i++) {
                    entity = objectClass.newInstance();
                    entityInMapFormat = listOfFormattedObjects.get(i);
                    mapIterator = entityInMapFormat.keySet().iterator();
                    while (mapIterator.hasNext()) {
                        String key = mapIterator.next();
                        Object value = entityInMapFormat.get(key);
                        for (int j = 0; j < methodsOfEntity.length; j++) {
                            Method method = methodsOfEntity[j];
                            if (method.getName().contains("set") && method.getName().substring(3).toLowerCase().equals(key.toLowerCase())) {
                                if (method.getName().contains("Id") && method.getName().substring(3).replace("Id", "").length() > 0 && !method.getName().equalsIgnoreCase("setComercialId")) {

                                    String resourceURL = "http://192.168.1.160:8080/WebGMWORK/webresources/" + key.substring(0, key.length() - 2) + "(" + value + ")";
                                    String requestMethod = "GET";
                                    //String serverResponse = OperacionesTodoDinamico.getServerResponse(resourceURL, requestMethod, null);
                                    Class classOfForeignObject = Class.forName("com.example.gmwork.proyectoandroid.Model." + key.substring(0, key.length() - 2));
                                  //  List<Map<String, Object>> foreignObjectInMapFormat = formatJsonInputWithOneEntry(serverResponse);
                                 //   List foreignObjectInListFormat = getObjectsFromFormattedJson(classOfForeignObject, foreignObjectInMapFormat, activity);
                                  //  value = foreignObjectInListFormat.get(0);
                                }
                                method.invoke(entity, value);
                            }
                        }
                    }
                    listOfEntities.add(entity);
                }
            } catch (Exception e) {
                System.out.println("Error in getObjectsFromFormattedJson: " + e.getMessage());
            }
            return listOfEntities;
        }

        //Converteix un objecte d'una classe coneguda en un string en format que pot ser enviat al servidor
        public static <T> String getJsonObjectFromEntity(Class<T> objectClass, T objectToTransform) {
            Field[] fieldsOfEntity = objectClass.getDeclaredFields();
            Method[] methodsOfEntity = objectClass.getDeclaredMethods();
            JSONObject stringJson = new JSONObject();
            try {
                for (int i = 0; i < fieldsOfEntity.length; i++) {
                    Field field = fieldsOfEntity[i];
                    String fieldName = field.getName().replace("_id", "Id");
                    for (int j = 0; j < methodsOfEntity.length; j++) {
                        Method method = methodsOfEntity[j];
                        if (method.getName().toLowerCase().contains("get") && method.getName().substring(3).toLowerCase().equalsIgnoreCase(fieldName.toLowerCase())) {
                            Object valueObtainedFromMethod = method.invoke(objectToTransform, null);
                        /**/
                            if (method.getName().contains("Id") && !method.getName().equals("getId") && !method.getName().equals("getComercialId")) {
                                Method m = valueObtainedFromMethod.getClass().getDeclaredMethod("getId");
                                valueObtainedFromMethod = m.invoke(valueObtainedFromMethod, null);
                            }
                        /**/
                            stringJson.put(fieldName, valueObtainedFromMethod);
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            return stringJson.toString();
        }

        public static <T> String getJsonObjectsFromEntitiesWithKnowsClass(Class<T> objectClass, List<T> objectsToTransform) {
            Field[] fieldsOfEntity = objectClass.getDeclaredFields();
            Method[] methodsOfEntity = objectClass.getDeclaredMethods();
            JSONObject stringJson = new JSONObject();
            JSONArray stringJsonArray = new JSONArray();
            try {
                for (int k = 0; k < objectsToTransform.size(); k++) {
                    for (int i = 0; i < fieldsOfEntity.length; i++) {
                        Field field = fieldsOfEntity[i];
                        for (int j = 0; j < methodsOfEntity.length; j++) {
                            Method method = methodsOfEntity[j];
                            if (method.getName().toLowerCase().contains("get") && method.getName().toLowerCase().contains(field.getName().toLowerCase())) {
                                stringJson.put(field.getName(), method.invoke(objectsToTransform.get(k), null));
                            }
                        }
                    }
                    stringJsonArray.put(stringJson);
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            return stringJsonArray.toString();
        }

//    private static <T> String foreignKeyResolver(Class<T> objectClass) {
//        String
//        switch(objectClass.getSimpleName()) {
//            case "Comanda":
//
//                break;
//        }
//
//    }
}
