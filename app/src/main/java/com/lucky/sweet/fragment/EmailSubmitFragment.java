package com.lucky.sweet.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lucky.sweet.R;
import com.lucky.sweet.utility.StringFormatUtility;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EmailSubmitFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EmailSubmitFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmailSubmitFragment extends Fragment {


    private EditText edt_userEmail;
    private EditText edt_verPassword;
    private Button btn_delete;
    private Button btn_nextStep;
    private Button btn_verCode;
    private String email;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_email_submit, container, false);
        initView(inflate);
        initEvent();
        return inflate;
    }

    private void initEvent() {
        btn_verCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edt_userEmail.getText().toString().trim();
                if (!email.isEmpty()) {
                    if (StringFormatUtility.checkoutEmail(email)) {

                        onEmailVer.checkOutEmail(email);


                    } else
                        Toast.makeText(getActivity(), "请输入正确邮箱", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getActivity(), "请输入账号", Toast.LENGTH_SHORT).show();

            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt_userEmail.setText("");
            }
        });
        btn_nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = edt_userEmail.getText().toString().trim();
                String verPsw = edt_verPassword.getText().toString().trim();
                if (!email.isEmpty() && !verPsw.isEmpty()) {

                    onEmailVer.onEmailVer(email, verPsw);

                } else
                    Toast.makeText(getActivity(), "请填写完整信息", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public interface OnEmailVer {
        void onEmailVer(String email, String verPsw);

        void checkOutEmail(String email);

    }

    private OnEmailVer onEmailVer;

    public void setOnEmailVerListener(OnEmailVer onEmailVer) {
        this.onEmailVer = onEmailVer;
    }

    public String getEmail() {
        return email;
    }

    private void initView(View inflate) {
        edt_userEmail = inflate.findViewById(R.id.edt_userEmail);
        edt_verPassword = inflate.findViewById(R.id.edt_verPassword);
        btn_delete = inflate.findViewById(R.id.btn_delete);
        btn_nextStep = inflate.findViewById(R.id.btn_nextStep);
        btn_verCode = inflate.findViewById(R.id.btn_verCode);

    }


}
