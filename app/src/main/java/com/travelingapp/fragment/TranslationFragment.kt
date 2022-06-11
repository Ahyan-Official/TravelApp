package com.travelingapp.fragment


import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.LENGTH_SHORT
import com.travelingapp.R
import com.travelingapp.model.api.TranslationResult
import com.travelingapp.model.room.AppBookmark
import com.travelingapp.model.room.AppHistory
import com.travelingapp.model.room.AppLanguage
import com.travelingapp.model.view.LanguagesViewModel
import com.travelingapp.model.view.TranslatorViewModel
import com.travelingapp.prefs.AppPrefs
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class TranslationFragment : Fragment(), TextWatcher, View.OnClickListener, CoroutineScope, View.OnFocusChangeListener {
    private lateinit var mTopBar: ViewGroup
    private lateinit var mTranslationHolder: ViewGroup
    private lateinit var mLanguagesHolder: ViewGroup
    private lateinit var mInputLangChoose: Button
    private lateinit var mInputLang: TextView
    private lateinit var mTranslationLangChoose: Button
    private lateinit var mTranslationLang: TextView
    private lateinit var mInput: EditText
    private lateinit var mTranslation: TextView
    private lateinit var mErase: MaterialButton
    private lateinit var mInputDone: MaterialButton
    private lateinit var mSwap: MaterialButton
    private lateinit var mLanguages: LanguagesViewModel

    private lateinit var mTranslator: TranslatorViewModel
    private var job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mLanguages = ViewModelProviders.of(this).get(LanguagesViewModel::class.java)
//        mHistory = ViewModelProviders.of(this).get(HistoryViewModel::class.java)
//        mBookmarks = ViewModelProviders.of(this).get(BookmarkViewModel::class.java)
        mTranslator = ViewModelProviders.of(this).get(TranslatorViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_translate, container, false)
        mTopBar = root.findViewById(R.id.topbar)

        mLanguagesHolder = root.findViewById(R.id.translation_languages_holder)
        mInputLangChoose = root.findViewById(R.id.input_lang_choose)
        mInputLang = root.findViewById(R.id.input_lang)
        mTranslationLangChoose = root.findViewById(R.id.result_lang_choose)
        mTranslationLang = root.findViewById(R.id.result_lang)
        mTranslationHolder = root.findViewById(R.id.primary_translation_holder)
        mTranslation = root.findViewById(R.id.result)

        mInput = root.findViewById(R.id.input)
        mInputDone = root.findViewById(R.id.done)
        mErase = root.findViewById(R.id.delete_input)
        mSwap = root.findViewById(R.id.swap)
        mInput.addTextChangedListener(this)
        mInput.onFocusChangeListener = this
        mInputLangChoose.setOnClickListener(this)
        mTranslationLangChoose.setOnClickListener(this)
        mInputDone.setOnClickListener(this)
        mErase.setOnClickListener(this)
        mSwap.setOnClickListener(this)

        return root
    }

    override fun onResume() {
        super.onResume()
        updateSwapState()
        updateLanguagesData()
        updateTranslationCard()
    }

    private fun updateSwapState() {
        mSwap.isEnabled = !AppPrefs.isAutoDetect(requireContext())
    }

    private fun updateLanguagesData() {
        mLanguages.getAllLanguages().observe(this, Observer { languages: List<AppLanguage> ->
            run {
                if (!AppPrefs.isAutoDetect(requireContext())) {
                    val inputLang = languages.find { appLanguage ->
                        appLanguage.id == AppPrefs.getDir(requireContext())[0]
                    }?.lang

                    Log.e("cv", "updateLanguagesData: "+inputLang )
                    mInputLangChoose.text = inputLang
                    mInputLang.text = inputLang
                } else {
                    mInputLangChoose.text = getString(R.string.autodetect)
                    mInputLang.text = getString(R.string.autodetect)
                }

                val translateLang = languages.find { appLanguage ->
                    appLanguage.id == AppPrefs.getDir(requireContext())[1]
                }?.lang


                Log.e("cv11", "updateLanguagesData: "+translateLang )

                mTranslationLangChoose.text = translateLang
                mTranslationLang.text = translateLang
            }
        })
    }


    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        val initialText = s.toString()
        if (initialText.isEmpty()) {
            mTranslation.clearComposingText()
            mTranslationHolder.visibility = View.GONE
            return
        }
    }

    override fun afterTextChanged(s: Editable?) {
        updateTranslationCard()
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        setCompactView(hasFocus)
    }

    private fun setCompactView(needCompact: Boolean) {
        mLanguagesHolder.visibility = if (needCompact) View.GONE else View.VISIBLE
        mTopBar.visibility = if (needCompact) View.GONE else View.VISIBLE
    }

    //@ObsoleteCoroutinesApi
    override fun onClick(v: View?) {
        var input = mInput.text.toString().trim()
        when (v) {
            mErase -> {
                launch {
                    if (input.isEmpty()) {
                        return@launch
                    }

                    mInput.editableText.clear()
                }
                hideKeyboard()
                mInput.clearFocus()
            }

            mSwap -> {
                val inputLang = AppPrefs.getDir(requireContext())[0]
                val translateLang = AppPrefs.getDir(requireContext())[1]
                AppPrefs.saveDir(requireContext(), translateLang!!, inputLang!!)
                updateLanguagesData()
                updateTranslationCard()
            }

            mInputDone -> {
                hideKeyboard()
                mInput.clearFocus()
            }

            mInputLangChoose -> {
                val bundle = Bundle()
                bundle.putString(LanguageChooseFragment.KEY_BUNDLE_DIRECTION, LanguageChooseFragment.DIRECTION_FROM)
                Navigation.findNavController(v).navigate(R.id.action_translationFragment_to_languageChooseDialogFragment, bundle)
            }

            mTranslationLangChoose -> {
                val bundle = Bundle()
                bundle.putString(LanguageChooseFragment.KEY_BUNDLE_DIRECTION, LanguageChooseFragment.DIRECTION_TO)
                Navigation.findNavController(v).navigate(R.id.action_translationFragment_to_languageChooseDialogFragment, bundle)
            }


        }
    }

    private fun updateTranslationCard() {
        launch {
            val input = mInput.text.toString().trim()

            if (input.isEmpty()) {
                mTranslationHolder.visibility = View.GONE
                setCompactView(false)
                return@launch
            }

            val result: TranslationResult? = if (AppPrefs.isAutoDetect(requireContext())) {
                mTranslator.translate(input, AppPrefs.getDirTo(requireContext()))
            } else {
                mTranslator.translate(input, AppPrefs.getDir(requireContext()))
            }

            if (AppPrefs.isAutoDetect(requireContext())) {
                mLanguages.getAllLanguages().observe(this@TranslationFragment, Observer { languages: List<AppLanguage> ->
                    run {
                        val langCode = result?.lang?.split("-")?.get(0)
                        val language = languages.find { language -> language.id == langCode }?.lang
                        val text = getString(R.string.autodetect) + ": " + language
                        mInputLang.text = text
                    }
                })
            }

            val isResultEmpty = result?.text.toString().trim().isEmpty()
            mTranslationHolder.visibility = if (!isResultEmpty) View.VISIBLE else View.GONE
            mTranslation.text = result?.text?.joinToString()
        }
    }

    private fun hideKeyboard() {
        val view = requireActivity().currentFocus
        if (view != null) {
            val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}
