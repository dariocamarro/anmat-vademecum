package com.thinkupstudios.anmat.vademecum;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.thinkupstudios.anmat.vademecum.bo.Component;
import com.thinkupstudios.anmat.vademecum.bo.Formula;
import com.thinkupstudios.anmat.vademecum.bo.FormularioBusqueda;
import com.thinkupstudios.anmat.vademecum.bo.MedicamentoBO;

import static android.R.anim.fade_in;
import static android.R.anim.fade_out;

/**
 * A fragment representing a single DetalleMedicamento detail screen.
 * This fragment is either contained in a {@link DetalleMedicamentoListActivity}
 * in two-pane mode (on tablets) or a {@link DetalleMedicamentoDetailActivity}
 * on handsets.
 */
public class DetalleMedicamentoDetailFragment extends Fragment implements View.OnClickListener {


    private MedicamentoBO medicamento;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DetalleMedicamentoDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(MedicamentoBO.MEDICAMENTOBO)) {
            this.medicamento = (MedicamentoBO) getArguments()
                    .getSerializable(MedicamentoBO.MEDICAMENTOBO);



        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detallemedicamento_detail, container, false);


        Resources res = getResources();

        TabHost tabs = (TabHost) rootView.findViewById(android.R.id.tabhost);
        tabs.setup();

        TabHost.TabSpec spec = tabs.newTabSpec("mitab1");
        spec.setContent(R.id.detalle);
        //spec.setIndicator("Detalle");
        spec.setIndicator("", res.getDrawable(R.drawable.details_blue_100));
        tabs.addTab(spec);
        tabs.setHorizontalScrollBarEnabled(true);

        spec = tabs.newTabSpec("");
        spec.setContent(R.id.formula);
        //spec.setIndicator("Formula");
        spec.setIndicator("", res.getDrawable(R.drawable.formula_blue_100));

        tabs.addTab(spec);

        tabs.setCurrentTab(0);
        this.setValores(rootView,this.medicamento,container);
        tabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String tabId) {
                Log.i("AndroidTabsDemo", "Pulsada pestaña: " + tabId);
            }
        });


         return rootView;
    }


    public void setValores(View rootView, MedicamentoBO m, ViewGroup container) {

        if(m.esRemediar()){
            rootView.findViewById(R.id.lbl_leyenda_remediar).setVisibility(View.VISIBLE);
        }else{
            rootView.findViewById(R.id.lbl_leyenda_remediar).setVisibility(View.GONE);
        }
        ((TextView) rootView.findViewById(R.id.condicion_de_expendioValor)).setText(m.getCondicionExpendio());
        ((TextView) rootView.findViewById(R.id.condicion_de_trazabilidadValor)).setText(m.getCondicionTrazabilidad());
        ((TextView) rootView.findViewById(R.id.forma_farmaceuticaValor)).setText(m.getForma());
        ((TextView) rootView.findViewById(R.id.gtinValor)).setText(m.getGtin());
        ((TextView) rootView.findViewById(R.id.laboratorioValor)).setText(m.getLaboratorio());
        ((TextView) rootView.findViewById(R.id.nombre_comercialValor)).setText(m.getNombreComercial());
        ((TextView) rootView.findViewById(R.id.nombre_generico_Valor)).setText(m.getNombreGenerico());
        ((TextView) rootView.findViewById(R.id.pais_industriaValor)).setText(m.getPaisIndustria());
        ((TextView) rootView.findViewById(R.id.nroCertificadoValor)).setText(m.getNumeroCertificado());
        ((TextView) rootView.findViewById(R.id.presentacionValor)).setText(m.getPresentacion());
        if(m.isEsUsoHospitalario()){
            ((TextView) rootView.findViewById(R.id.lbl_precio)).setText(m.getUsoHospitalarioLabel());
            ((TextView) rootView.findViewById(R.id.precioValor)).setText(" ");
        }else{
            ((TextView) rootView.findViewById(R.id.lbl_precio)).setText(getResources().getString(R.string.precio));
            ((TextView) rootView.findViewById(R.id.precioValor)).setText(m.getPrecio());
        }

        ((TextView) rootView.findViewById(R.id.troquelValor)).setText(m.getTroquel());
        this.cargarSolapaFormula(m.getFormula(), container, rootView);
    }

    private void cargarSolapaFormula(Formula formula, ViewGroup container, View rootView) {
        LayoutInflater mInflater = (LayoutInflater) this.getActivity()
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        LinearLayout tablaFormula = (LinearLayout) rootView.findViewById(R.id.formula_content);

        View formulaRow;
        for (Component component : formula.getComponents()) {

            formulaRow = mInflater.inflate(R.layout.formula, container, false);
            ((TextView) formulaRow.findViewById(R.id.active_component)).setText(component.getActiveComponent());
            ((TextView) formulaRow.findViewById(R.id.proportion)).setText(component.getProportion());
            formulaRow.setOnClickListener(this);
            format(formulaRow);
            tablaFormula.addView(formulaRow);

        }


    }

    private void format(View formulaRow) {
        int color = formulaRow.getResources().getColor(R.color.anmat_azul);

        ((TextView) formulaRow.findViewById(R.id.active_component)).setTextColor(color);
        ((TextView) formulaRow.findViewById(R.id.proportion)).setTextColor(color);

    }

    @Override
    public void onClick(View v) {
        String principioActivo = ((TextView) v.findViewById(R.id.active_component)).getText().toString();
        Intent i = new Intent(this.getActivity(),
                DetallePrincipioActivoActivity.class);
        i.putExtra(FormularioBusqueda.PRINCIPIO_ACTIVO, principioActivo);
        startActivity(i);
        this.getActivity()
                .overridePendingTransition(fade_in, fade_out);
    }
}
