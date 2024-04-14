package vn.edu.ptit.planta.ui.myplant.myplantdetail.about;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vn.edu.ptit.planta.R;
import vn.edu.ptit.planta.databinding.FragmentAboutBinding;
import vn.edu.ptit.planta.model.AttributeOfMyPlant;
import vn.edu.ptit.planta.model.myplant.MyPlant;
import vn.edu.ptit.planta.ui.myplant.editmyplant.EditMyPlantActivity;

public class AboutFragment extends Fragment {
    private FragmentAboutBinding binding;
    private RecyclerView recyclerView;
    private AboutAdapter aboutAdapter;
    private AboutViewModel aboutViewModel;
    private Bundle bundle;
    private LinearLayout layoutExpandImageMyPlant, layoutContentImageMyPlant;
    private ImageView ivImageMyPlant;
    private CheckBox cbImageMyPlant;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_about, container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int idUser = sharedPreferences.getInt("id",0);

        bundle = getArguments();
        MyPlant myPlant = null;
        if(bundle.containsKey("myplant")){
            myPlant = (MyPlant) bundle.getSerializable("myplant");
        }
        aboutViewModel = new ViewModelProvider(this).get(AboutViewModel.class);
        // Trang My Plant vào bundle
        if(myPlant != null) {
            aboutViewModel.setIdUserAndMyPlant(idUser, myPlant.getId());
        }else {
            aboutViewModel.setIdUserAndMyPlant(idUser, bundle.getInt("id_myplant"));
        }
        //
        binding.setAboutViewModel(aboutViewModel);
        binding.setLifecycleOwner(this);

        initRecycleView();
        return binding.getRoot();
    }
    private void initRecycleView(){
        recyclerView = binding.recyclerviewAbout;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        aboutViewModel.getMyPlant().observe(requireActivity(), new Observer<MyPlant>() {
            @Override
            public void onChanged(MyPlant myPlant) {
                List<AttributeOfMyPlant> attributeOfMyPlants = getListAttributeOfMyPlant(myPlant);

                if(aboutAdapter == null){
                    aboutAdapter = new AboutAdapter(attributeOfMyPlants);
                    recyclerView.setAdapter(aboutAdapter);
                }
                else{
                    aboutAdapter.updateData(attributeOfMyPlants);
                }

                layoutExpandImageMyPlant = binding.layoutExpandImage;
                layoutContentImageMyPlant = binding.layoutContentMyplanta;
                ivImageMyPlant = binding.ivImageMyplanta;
                cbImageMyPlant = binding.checkbox;

                glideImage(myPlant.getImage(), ivImageMyPlant);
                layoutContentImageMyPlant.setVisibility(View.GONE);
                layoutExpandImageMyPlant.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        expand(layoutContentImageMyPlant, cbImageMyPlant);
                    }
                });
                cbImageMyPlant.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        expand(layoutContentImageMyPlant, cbImageMyPlant);
                    }
                });
            }
        });
    }
    public void expand(LinearLayout layout, CheckBox checkBox){
        if(layout.getVisibility() == View.GONE) {
            layout.setVisibility(View.VISIBLE);
            checkBox.setChecked(true);
        }
        else {
            layout.setVisibility(View.GONE);
            checkBox.setChecked(false);
        }
    }

    public void glideImage(String image, ImageView imageView) {
        if (image != null) {
            byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

            Glide.with(AboutFragment.this)
                    .load(bitmap)
                    .placeholder(R.drawable.icon_no_mob)
                    .into(imageView);
        } else {
            imageView.setImageResource(R.drawable.icon_no_image);
        }
    }

    private List<AttributeOfMyPlant> getListAttributeOfMyPlant(MyPlant myPlant) {
        List<AttributeOfMyPlant> attributeOfMyPlants = new ArrayList<>();
        attributeOfMyPlants.add(new AttributeOfMyPlant("Tên thực vật",myPlant.getName()));
        attributeOfMyPlants.add(new AttributeOfMyPlant("Loại cây",myPlant.getPlantDetailOfMyPlant().getTypePlant()));
        attributeOfMyPlants.add(new AttributeOfMyPlant("Ngày trồng",myPlant.getGrownDate()));
        attributeOfMyPlants.add(new AttributeOfMyPlant("Loại ánh sáng",myPlant.getKindOfLight()));
        attributeOfMyPlants.add(new AttributeOfMyPlant("Kích thước trưởng thành",myPlant.getPlantDetailOfMyPlant().getMatureSize()));
        attributeOfMyPlants.add(new AttributeOfMyPlant("Thời gian trưởng thành",myPlant.getPlantDetailOfMyPlant().getMatureTime()));
        attributeOfMyPlants.add(new AttributeOfMyPlant("Mô tả",myPlant.getPlantDetailOfMyPlant().getDescription()));

        return attributeOfMyPlants;
    }
}