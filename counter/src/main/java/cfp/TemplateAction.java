package cfp;


import java.awt.List;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.change_vision.jude.api.inf.AstahAPI;
import com.change_vision.jude.api.inf.exception.ProjectNotFoundException;
import com.change_vision.jude.api.inf.model.IClass;
import com.change_vision.jude.api.inf.model.IERModel;
import com.change_vision.jude.api.inf.model.IModel;
import com.change_vision.jude.api.inf.model.INamedElement;
import com.change_vision.jude.api.inf.model.IPackage;
import com.change_vision.jude.api.inf.project.ProjectAccessor;
import com.change_vision.jude.api.inf.ui.IPluginActionDelegate;
import com.change_vision.jude.api.inf.ui.IWindow;

public class TemplateAction implements IPluginActionDelegate {

	public Object run(IWindow window) throws UnExpectedException {
	    try {
	        AstahAPI api = AstahAPI.getAstahAPI();
	        ProjectAccessor projectAccessor = api.getProjectAccessor();
	        IModel ICP = projectAccessor.getProject();
	        ArrayList<IClass> classList = new ArrayList<IClass>();
	        getAllClasses(ICP,classList);	        
	        JOptionPane.showMessageDialog(window.getParent(),"これはイチハラプラグインテストだよ！count=>"+classList.size()+"class(es)");
	    } catch (ProjectNotFoundException e) {
	        String message = "Project is not opened.Please open the project or create new project.";
			JOptionPane.showMessageDialog(window.getParent(), message, "Warning", JOptionPane.WARNING_MESSAGE); 
	    } catch (Exception e) {
	    	JOptionPane.showMessageDialog(window.getParent(), "Unexpected error has occurred.", "Alert", JOptionPane.ERROR_MESSAGE); 
	        throw new UnExpectedException();
	    }
	    return null;
	}
	  public class CalculateUnExpectedException extends UnExpectedException {
	  }

	  private void getAllClasses(INamedElement element, ArrayList<IClass> classList)
	      throws ClassNotFoundException, ProjectNotFoundException {
	    if (element instanceof IPackage) {
	      for(INamedElement ownedNamedElement : ((IPackage)element).getOwnedElements()) {
	        getAllClasses(ownedNamedElement, classList);
	      }
	    } else if (element instanceof IClass) {
	      classList.add((IClass)element);
	      for(IClass nestedClasses : ((IClass)element).getNestedClasses()) {
	        getAllClasses(nestedClasses, classList);
	      }
	    }
	  }

}
