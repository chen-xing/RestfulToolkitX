package jiux.net.plugin.restful.common.resolver;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiModifierList;
import com.intellij.psi.impl.java.stubs.index.JavaAnnotationIndex;
import com.intellij.psi.search.GlobalSearchScope;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import jiux.net.plugin.restful.annotations.RestServerAnnotation;
import jiux.net.plugin.restful.common.restserver.RestServerAnnotationHelper;
import jiux.net.plugin.restful.method.RequestPath;
import jiux.net.plugin.restful.method.action.PropertiesHandler;
import jiux.net.plugin.restful.navigation.action.RestServiceItem;

public class RestServerResolver extends BaseServiceResolver {
    PropertiesHandler propertiesHandler;

    public RestServerResolver(Module module) {
        this.myModule = module;
        this.propertiesHandler = new PropertiesHandler(module);
    }

    public RestServerResolver(Project project) {
        this.myProject = project;
    }

    public List<RestServiceItem> getRestServiceItemList(
            Project project, GlobalSearchScope globalSearchScope) {
        List<RestServiceItem> itemList = new ArrayList<>();
        RestServerAnnotation[] restServerAnnotations = RestServerAnnotation.values();
        for (RestServerAnnotation restServerAnnotation : restServerAnnotations) {
            Collection<PsiAnnotation> psiAnnotations =
                    JavaAnnotationIndex.getInstance()
                            .get(restServerAnnotation.getShortName(), project, globalSearchScope);
            for (PsiAnnotation psiAnnotation : psiAnnotations) {
                PsiModifierList psiModifierList = (PsiModifierList) psiAnnotation.getParent();
                PsiElement psiElement = psiModifierList.getParent();
                PsiClass psiClass = (PsiClass) psiElement;
                List<RestServiceItem> serviceItemList = getServiceItemList(psiClass);
                itemList.addAll(serviceItemList);
            }
        }
        return itemList;
    }

    protected List<RestServiceItem> getServiceItemList(PsiClass psiClass) {
        PsiMethod[] psiMethods = psiClass.getMethods();
        List<RestServiceItem> itemList = new ArrayList<>();
        List<RequestPath> classRequestPaths = RestServerAnnotationHelper.getRequestPaths(psiClass);
        for (PsiMethod psiMethod : psiMethods) {
            List<RequestPath> methodRequestPaths =
                    RestServerAnnotationHelper.getRequestPaths(psiMethod);
            for (RequestPath classRequestPath : classRequestPaths) {
                for (RequestPath methodRequestPath : methodRequestPaths) {
                    String path = classRequestPath.getPath();
                    RestServiceItem item =
                            createRestServiceItem((PsiElement) psiMethod, path, methodRequestPath);
                    itemList.add(item);
                }
            }
        }
        return itemList;
    }
}
