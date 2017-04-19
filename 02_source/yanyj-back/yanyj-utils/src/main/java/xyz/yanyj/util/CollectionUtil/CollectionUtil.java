package xyz.yanyj.util.CollectionUtil;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import xyz.yanyj.util.StringUtil.StringUtil;

import java.util.*;


/**
 * Created by yanyj on 2017/4/19.
 */
public class CollectionUtil {
    private CollectionUtil() {
    }

    public static <T> List<T> filter(List<T> unfiltered, Predicate<? super T> predicate) {
        return Lists.newArrayList(Iterables.filter(unfiltered, predicate));
    }

    public static <T> T find(List<T> list, Predicate<? super T> predicate) {
        return (T) Iterables.find(list, (Predicate<? super Object>) predicate, (Object)null);
    }

    public static Map<String, Object> queryStringToMap(String queryString) {

        if(queryString.contains("#")) {
            queryString = queryString.substring(0, queryString.indexOf("#"));
        }

        ArrayList list = Lists.newArrayList(queryString.split("&"));
        HashMap result = new HashMap(list.size());
        Iterator i$ = list.iterator();

        while(i$.hasNext()) {
            String item = (String)i$.next();
            String[] keyValuePair = item.split("=");
            result.put(keyValuePair[0], keyValuePair[1]);
        }

        return result;
    }


    public static <T> List<T> union(Collection<T> a, Collection<T> b) {
        ArrayList result = new ArrayList(a);
        result.addAll(b);
        return result;
    }

    public static <T> List<T> subtract(Collection<T> a, Collection<T> b) {
        ArrayList list = new ArrayList(a);
        Iterator i$ = b.iterator();

        while(i$.hasNext()) {
            Object element = i$.next();
            list.remove(element);
        }

        return list;
    }

    public static <T> List<T> intersection(Collection<T> a, Collection<T> b) {
        ArrayList list = new ArrayList();
        Iterator i$ = a.iterator();

        while(i$.hasNext()) {
            Object element = i$.next();
            if(b.contains(element)) {
                list.add(element);
            }
        }

        return list;
    }

    public static List<Map<String, Object>> listToTree(List<Map<String, Object>> datas, final String idField, String pIdField) {
        if(datas != null && datas.size() != 0) {
            ArrayList rootDatas = new ArrayList(datas.size());
            int length = datas.size();
            Map data = null;

            for(int i = 0; i < length; ++i) {
                data = (Map)datas.get(i);
                final String pId = (String)data.get(pIdField);
                Map target;
                if(pId == null) {
                    target = null;
                } else {
                    target = (Map)find(datas, new Predicate() {
                        public boolean apply(Object item) {

                            return ((Map)item).get(idField).equals(pId);
                        }

                    });
                }

                if(target == null) {
                    rootDatas.add(data);
                }
            }

            iterativeData(datas, rootDatas, idField, pIdField);
            return rootDatas;
        } else {
            return datas;
        }
    }

    private static List<Map<String, Object>> listToTree(List<Map<String, Object>> datas, String idField, String pIdField, String nameField, Map<String, String> params, String... includeField) {

        String targetIdField = (String)params.get("id");
        String targetPIdField = (String)params.get("pId");
        String childrenName = (String)params.get("children");
        String name = (String)params.get("name");
        if(!StringUtil.isNullOrEmpty(targetIdField) && !StringUtil.isNullOrEmpty(targetPIdField) && !StringUtil.isNullOrEmpty(childrenName) && !StringUtil.isNullOrEmpty(name)) {
            List results = listToTree(datas, idField, pIdField);
            if(targetIdField.equals(idField) && targetPIdField.equals(pIdField) && childrenName.equals("children") && nameField.equals(name)) {
                return results;
            } else {
                iterativeChangeKeys(results, idField, pIdField, nameField, params, includeField);
                return results;
            }
        } else {
            throw new IllegalArgumentException("params 的 id, pId, children, name 参数必须同时指定，当前四个参数分别为");
        }
    }

    public static void main(String[] args) {
        ArrayList datas = new ArrayList();
        HashMap map = new HashMap();
        map.put("ID", "1");
        map.put("PID", (Object)null);
        map.put("NAME", "top");
        map.put("asdf", "top");
        datas.add(map);
        map = new HashMap();
        map.put("ID", "2");
        map.put("PID", "1");
        map.put("NAME", "level1-1");
        datas.add(map);
        map = new HashMap();
        map.put("ID", "3");
        map.put("PID", "1");
        map.put("NAME", "level1-2");
        datas.add(map);
        map = new HashMap();
        map.put("ID", "4");
        map.put("PID", "2");
        map.put("NAME", "level2-1");
        datas.add(map);
        map = new HashMap();
        map.put("ID", "5");
        map.put("PID", "4");
        map.put("NAME", "level3-1");
        datas.add(map);
        System.out.println(listToZtree(datas, "ID", "PID", "NAME", new String[]{"asdf", "NAME"}));
    }

    public static String listToZtree(List<Map<String, Object>> datas, String idField, String pIdField, String nameField, String... includeField) {
        HashMap param = new HashMap(4);
        param.put("id", "id");
        param.put("pId", "pId");
        param.put("children", "children");
        param.put("name", "name");
        List lists = listToTree(datas, idField, pIdField, nameField, param, includeField);
        return JSON.toJSONString(lists);
    }

    private static void iterativeChangeKeys(List<Map<String, Object>> results, String idField, String pIdField, String nameField, Map<String, String> params, String... includeField) {
        String targetIdField = (String)params.get("id");
        String targetPIdField = (String)params.get("pId");
        String childrenName = (String)params.get("children");
        String targetNameField = (String)params.get("name");
        String[] includeFields = includeField;
        HashMap includeFieldVal = null;
        Iterator i$ = results.iterator();

        while(i$.hasNext()) {
            Map map = (Map)i$.next();
            Object id = map.get(idField);
            Object pId = map.get(pIdField);
            Object name = map.get(nameField);
            Object children = map.get("children");
            String[] arr$;
            int len$;
            int i$1;
            String key;
            if(includeFields != null) {
                includeFieldVal = new HashMap(includeFields.length);
                arr$ = includeFields;
                len$ = includeFields.length;

                for(i$1 = 0; i$1 < len$; ++i$1) {
                    key = arr$[i$1];
                    includeFieldVal.put(key, map.get(key));
                }
            }

            map.clear();
            map.put(targetIdField, id);
            map.put(targetPIdField, pId);
            map.put(targetNameField, name);
            if(includeFields != null) {
                arr$ = includeFields;
                len$ = includeFields.length;

                for(i$1 = 0; i$1 < len$; ++i$1) {
                    key = arr$[i$1];
                    map.put(key, includeFieldVal.get(key));
                }
            }

            if(children != null) {
                map.put(childrenName, children);
                iterativeChangeKeys((List)map.get(childrenName), idField, pIdField, nameField, params, includeField);
            }
        }

    }

    private static void iterativeData(List<Map<String, Object>> datas, List<Map<String, Object>> parentDatas, String idField, String parentIdFiled) {
        int length = parentDatas.size();
        Map data = null;

        for(int i = 0; i < length; ++i) {
            data = (Map)parentDatas.get(i);
            ArrayList children = new ArrayList();
            data.put("children", children);
            int len = datas.size();
            Map d = null;

            for(int j = 0; j < len; ++j) {
                d = (Map)datas.get(j);
                if(data.get(idField).equals(d.get(parentIdFiled))) {
                    children.add(d);
                    datas.remove(j);
                    --j;
                    --len;
                }
            }

            iterativeData(datas, children, idField, parentIdFiled);
        }

    }

}
