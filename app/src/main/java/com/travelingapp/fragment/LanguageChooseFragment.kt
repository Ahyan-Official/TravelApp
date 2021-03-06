package com.travelingapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.switchmaterial.SwitchMaterial
import com.travelingapp.R
import com.travelingapp.activity.MainTranslationActivity
import com.travelingapp.adapter.LanguageAdapter
import com.travelingapp.model.room.AppLanguage
import com.travelingapp.model.view.LanguagesViewModel
import com.travelingapp.prefs.AppPrefs
import com.travelingapp.utils.RecyclerItemClickListener


class LanguageChooseFragment : Fragment() {
    companion object {
        const val KEY_BUNDLE_DIRECTION = "key_bundle_direction"
        const val DIRECTION_FROM = "direction_from"
        const val DIRECTION_TO = "direction_to"
    }

    lateinit var mLanguagesViewModel: LanguagesViewModel
    lateinit var mRecyclerView: RecyclerView
    lateinit var mAutodetectSwitch: SwitchMaterial
    lateinit var mClose: MaterialButton
    lateinit var mAdapter: LanguageAdapter
    lateinit var mDirection: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mLanguagesViewModel = ViewModelProviders.of(this).get(LanguagesViewModel::class.java)
        mDirection = arguments?.getString(KEY_BUNDLE_DIRECTION)!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_language_choose, container, false)

        mClose = root.findViewById(R.id.close)
        mClose.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_languageChooseDialogFragment_to_translationFragment)
        }

        mAutodetectSwitch = root.findViewById(R.id.autodetection)
        mAutodetectSwitch.isChecked = AppPrefs.isAutoDetect(requireContext())
        mAutodetectSwitch.visibility = if (mDirection == DIRECTION_FROM) View.VISIBLE else View.GONE
        mAutodetectSwitch.setOnCheckedChangeListener { _: CompoundButton, b: Boolean ->
            mRecyclerView.visibility = if (b) View.GONE else View.VISIBLE
            AppPrefs.setAutoDetect(requireContext(), b)
        }

        mAdapter = LanguageAdapter(requireContext())
        mLanguagesViewModel.getAllLanguages().observe(viewLifecycleOwner, Observer { languages: List<AppLanguage> -> mAdapter.data = languages })

        mRecyclerView = root.findViewById(R.id.list)
        mRecyclerView.isEnabled = mDirection == DIRECTION_FROM && !mAutodetectSwitch.isChecked
        mRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        mRecyclerView.adapter = mAdapter
        mRecyclerView.addOnItemTouchListener(
                RecyclerItemClickListener(requireContext(), mRecyclerView, object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onLongItemClick(view: View?, position: Int) {}

                    override fun onItemClick(view: View?, position: Int) {
                        val data = mAdapter.data[position].id
                        when (mDirection) {
                            DIRECTION_FROM -> AppPrefs.saveDirFrom(requireContext(), data)
                            DIRECTION_TO -> AppPrefs.saveDirTo(requireContext(), data)
                        }
                        Navigation.findNavController(requireView()).navigate(R.id.action_languageChooseDialogFragment_to_translationFragment)
                    }
                }))
        return root
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as MainTranslationActivity).setNavigationViewVisibility(false)
    }

    override fun onPause() {
        super.onPause()
        (requireActivity() as MainTranslationActivity).setNavigationViewVisibility(true)
    }
}