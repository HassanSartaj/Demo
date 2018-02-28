package mdse.emf.main;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;

import mdse.emf.util.ModelLoader;
/**
 * A class that reads UML class diagram in XMI format and prints its elements.
 * 
 * @author Hassan Sartaj
 * @version 1.0
 */
public class UMLClassDiagramReader {

	public static void main(String[] args) {
		ModelLoader umlModel = new ModelLoader();
		String umlFilePath = "Examples/accountmodel_papyrus.uml";
//		String umlFilePath = "Examples/TestExample01.uml";
		String uri = null;
		try {
			uri = umlModel.getFileURI(umlFilePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Object objModel = umlModel.loadModel(uri);
		Model sourceModel;
		EList<PackageableElement> sourcePackagedElements = null;
		if (objModel instanceof Model) {
			sourceModel = (Model) objModel;
			sourcePackagedElements = sourceModel.getPackagedElements();
		} else if (objModel instanceof Package) {
			Package sourcePackage = (Package) objModel;
			sourcePackagedElements = sourcePackage.getPackagedElements();
		}

		for (PackageableElement element : sourcePackagedElements){
			//for nested package
			if(element.eClass() == UMLPackage.Literals.PACKAGE){
				org.eclipse.uml2.uml.Package nestedPackage = (org.eclipse.uml2.uml.Package) element;
				EList<PackageableElement> nestedPackagedElements = nestedPackage.getPackagedElements();
				for (PackageableElement nestedElement : nestedPackagedElements){
					printModelDetails(nestedElement);
				}
			}
			else
				printModelDetails(element);
		}
	}

	private static void printModelDetails(PackageableElement element){
		if (element.eClass() == UMLPackage.Literals.CLASS)
		{
			Class clas = (Class)element;
			//get class name
			String clasName = clas.getName();
			System.out.println("Class Name: "+clasName);
			//get class attributes
			EList<Property> attributes = clas.getOwnedAttributes();				
			System.out.println("Attributes: ");
			if(!attributes.isEmpty()){
				for (Property attr: attributes)
				{
					System.out.println(attr.getName());
				}
			}
		}
		else if (element.eClass() == UMLPackage.Literals.ASSOCIATION)
		{
			Association association = (Association)element;
			EList<Property> memberEnds = association.getMemberEnds();
			System.out.println("\nAssociation Ends: \n" );
			for(Property end: memberEnds){
				System.out.println("End name: "+end.getName());
			}
		}
	}
}
