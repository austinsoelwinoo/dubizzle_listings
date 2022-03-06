package com.dubizzle.listings.presentation.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.dubizzle.core.domain.Listing;
import com.dubizzle.listings.databinding.AcitivityDetailsBinding;

public class DDetailsActivity extends AppCompatActivity {
    public static final String INTENT_EXTRA_PARAM_LISTING_CREATED_AT = "INTENT_EXTRA_PARAM_LISTING_CREATED_AT";
    public static final String INTENT_EXTRA_PARAM_LISTING_PRICE = "INTENT_EXTRA_PARAM_LISTING_PRICE";
    public static final String INTENT_EXTRA_PARAM_LISTING_NAME = "INTENT_EXTRA_PARAM_LISTING_NAME";
    public static final String INTENT_EXTRA_PARAM_LISTING_IMAGE_URL = "INTENT_EXTRA_PARAM_LISTING_IMAGE_URL";

    public static Intent getCallingIntent(Context context, Listing listing) {
        Intent callingIntent = new Intent(context, DDetailsActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_LISTING_CREATED_AT, listing.getCreatedAt());
        callingIntent.putExtra(INTENT_EXTRA_PARAM_LISTING_PRICE, listing.getPrice());
        callingIntent.putExtra(INTENT_EXTRA_PARAM_LISTING_NAME, listing.getName());
        if (listing.getImageUrls().size() > 0)
            callingIntent.putExtra(INTENT_EXTRA_PARAM_LISTING_IMAGE_URL, listing.getImageUrls().get(0));
        return callingIntent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AcitivityDetailsBinding binding = AcitivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String createdAt = getIntent().getStringExtra(INTENT_EXTRA_PARAM_LISTING_CREATED_AT);
        String price = getIntent().getStringExtra(INTENT_EXTRA_PARAM_LISTING_PRICE);
        String name = getIntent().getStringExtra(INTENT_EXTRA_PARAM_LISTING_NAME);
        String imageUrl = getIntent().getStringExtra(INTENT_EXTRA_PARAM_LISTING_IMAGE_URL);

        Glide.with(this)
                .load(imageUrl)
                .into(binding.ivListingImage);
        binding.tvListingName.setText(name);
        binding.tvListingPrice.setText(price);
        binding.tvListingCreated.setText(createdAt);
    }
}
