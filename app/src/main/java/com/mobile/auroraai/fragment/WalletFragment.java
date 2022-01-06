package com.mobile.auroraai.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import com.mobile.auroraai.R;
import com.mobile.auroraai.api.ServerCallback;
import com.mobile.auroraai.core.TagAble;
import com.mobile.auroraai.entity.Wallet;
import com.mobile.auroraai.helper.JsonHelper;
import com.mobile.auroraai.service.ServiceHolder;
import com.mobile.auroraai.utils.CustomHashMap;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class WalletFragment extends Fragment implements TagAble {

    private static final String[] operationChoice = {"Add", "Deduct", "Transfer"};
    private static String[] typeChoice = {};
    private static final CustomHashMap<Long, String> walletMap = new CustomHashMap<>();
    private Spinner typeChoiceSpinner;
    private Spinner walletFromChoiceSpinner;
    private Spinner walletToChoiceSpinner;

    public WalletFragment() {
        super(R.layout.wallet_fragment);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View view = super.onCreateView(inflater, container, savedInstanceState);
        assert view != null;

        getTransactionType();
        getWalletList();

        // -----------------------------------------------------------------------------------------
        typeChoiceSpinner = view.findViewById(R.id.wallet_type_dropdown);
        walletFromChoiceSpinner = view.findViewById(R.id.wallet_from_dropdown);
        walletToChoiceSpinner = view.findViewById(R.id.wallet_to_dropdown);
        final Spinner operationChoiceSpinner = view.findViewById(R.id.wallet_operation_dropdown);

        final Button submitTransaction = view.findViewById(R.id.wallet_submit_operation);
        final EditText amountInputText = view.findViewById(R.id.wallet_amount_text_input);
        final EditText referenceInputText = view.findViewById(R.id.wallet_reference_text_input);
        // -----------------------------------------------------------------------------------------

        final ArrayAdapter<String> operationAdapter = new ArrayAdapter<>(this.getActivity(),
                android.R.layout.simple_spinner_item, operationChoice);

        operationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        operationChoiceSpinner.setAdapter(operationAdapter);

        operationChoiceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (operationChoiceSpinner.getSelectedItem() == null) {
                    return;
                }
                final String operation = operationChoiceSpinner.getSelectedItem().toString();
                // todo: ugly
                switch (operation) {
                    case "Add":
                        disabledDropdown(walletFromChoiceSpinner);
                        enabledDropdown(walletToChoiceSpinner);
                        break;
                    case "Deduct":
                        enabledDropdown(walletFromChoiceSpinner);
                        disabledDropdown(walletToChoiceSpinner);
                        break;
                    case "Transfer":
                        enabledDropdown(walletFromChoiceSpinner);
                        enabledDropdown(walletToChoiceSpinner);
                        break;
                    default:
                        throw new UnsupportedOperationException("Unable to determine operation: " + operation);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submitTransaction.setOnClickListener(
                view1 -> {
                    validateNotEmpty(amountInputText);
                    validateNotEmpty(referenceInputText);

                    final String amountInput = extractStringFromTextInput(amountInputText);
                    final String operationChoiceSelected = operationChoiceSpinner.getSelectedItem().toString();
                    final String referenceInput = extractStringFromTextInput(referenceInputText);
                    final String typeChoiceSelected = typeChoiceSpinner.getSelectedItem().toString();

                    final String walletFromChoiceSelected = walletFromChoiceSpinner.getSelectedItem().toString();
                    final String walletToChoiceSelected = walletToChoiceSpinner.getSelectedItem().toString();

                    final Long fromWalletId = walletMap.getKeyUsingValue(walletFromChoiceSelected);
                    final Long toWalletId = walletMap.getKeyUsingValue(walletToChoiceSelected);

                    ServiceHolder.getWalletAPIService().submitTransaction(
                            getActivity(),
                            new BigDecimal(amountInput),
                            operationChoiceSelected,
                            referenceInput,
                            typeChoiceSelected, fromWalletId, toWalletId,
                            new ServerCallback<JsonNull>() {
                                @Override
                                public String getClassTag() {
                                    return WalletFragment.this.getClassTag();
                                }
                            }
                    );

                    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setCancelable(false);
                    builder.setTitle("Success!");
                    builder.setMessage("Your transaction is successful");

                    builder.setPositiveButton("Done", (dialog, which) -> {
                        // todo: refactor
                        final NavOptions navOptions = new NavOptions.Builder().setPopUpTo(R.id.main, true).build();
                        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.homepage_screen, null, navOptions);
                    });
                    builder.show();
                });
        return view;
    }

    // todo: make as utils
    private void enabledDropdown(final Spinner spinner) {
        spinner.setEnabled(true);
        spinner.setClickable(true);
    }

    private void disabledDropdown(final Spinner spinner) {
        spinner.setEnabled(false);
        spinner.setClickable(false);
    }

    private void getTransactionType() {
        ServiceHolder.getTransactionAPIService().getTypeChoice(getActivity(), new ServerCallback<JsonArray>() {

            @Override
            public String getClassTag() {
                return WalletFragment.this.getClassTag();
            }

            @Override
            public void onSuccess(final JsonArray response) {
                try {
                    typeChoice = JsonHelper.convertToPrimitiveArray(response);
                } catch (Exception e) {
                    logError("Unable to parse json array to primitive array", e);
                }
                final ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(WalletFragment.this.getActivity(),
                        android.R.layout.simple_spinner_item, typeChoice);
                typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                typeChoiceSpinner.setAdapter(typeAdapter);
            }
        });
    }

    private void getWalletList() {
        ServiceHolder.getWalletAPIService().getAllWallet(getActivity(), new ServerCallback<JsonArray>() {

            @Override
            public String getClassTag() {
                return WalletFragment.this.getClassTag();
            }

            @Override
            public void onSuccess(JsonArray response) {
                List<Wallet> walletList = new ArrayList<>();
                try {
                    walletList = JsonHelper.convertToList(response, Wallet.class);
                } catch (Exception e) {
                    logError("Unable to parse json array to primitive array", e);
                }
                for (final Wallet wallet : walletList) {
                    walletMap.put(wallet.id, wallet.name);
                }
                final ArrayAdapter<String> walletAdapter = new ArrayAdapter<>(WalletFragment.this.getActivity(),
                        android.R.layout.simple_spinner_item, new ArrayList<>(walletMap.values()));

                walletAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                walletFromChoiceSpinner.setAdapter(walletAdapter);
                walletToChoiceSpinner.setAdapter(walletAdapter);
            }
        });
    }

    /**
     * make as utils
     *
     * @return
     */
    private String extractStringFromTextInput(final EditText editText) {
        return editText.getText().toString().trim();
    }


    /**
     * make as utils
     *
     * @param editText
     */
    private void validateNotEmpty(final EditText editText) {
        if (StringUtils.isBlank(extractStringFromTextInput(editText))) {
            editText.setError("Field cannot be blank");
        }
    }

    @Override
    public String getClassTag() {
        return "WalletFragment";
    }
}
