package in.therenter.therenterfirebase;

import com.github.dkharrat.nexusdialog.FormWithAppCompatActivity;
import com.github.dkharrat.nexusdialog.controllers.EditTextController;
import com.github.dkharrat.nexusdialog.controllers.FormSectionController;
import com.github.dkharrat.nexusdialog.controllers.SelectionController;

import java.util.Arrays;

public class ProductsActivity extends FormWithAppCompatActivity {

    @Override
    protected void initForm() {
        setTitle("The Renter Products");

        FormSectionController section = new FormSectionController(this, "Add Product");
        section.addElement(new EditTextController(this, "name", "Name"));
        section.addElement(new EditTextController(this, "brand", "Brand"));
        section.addElement(new EditTextController(this, "model", "Model"));
        section.addElement(new EditTextController(this, "type", "Projector"));
        section.addElement(new SelectionController(this, "delivery_type", "Delivery Type", true, "Select", Arrays.asList("Home Delivery", "Takeaway"), true));
        section.addElement(new SelectionController(this, "shipping_type", "Shipping Type", true, "Select", Arrays.asList("Paid", "Free", "N/A"), true));
        section.addElement(new EditTextController(this, "shipping_charge", "Shipping Charge"));
    }
}