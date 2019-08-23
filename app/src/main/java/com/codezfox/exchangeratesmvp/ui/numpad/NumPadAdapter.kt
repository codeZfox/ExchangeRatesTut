package com.niveler.numpad

import com.hannesdorfmann.adapterdelegates3.AdapterDelegatesManager
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter

class NumPadAdapter(

        items: List<Button>,

        private var onClick: ((button: Button) -> Unit)? = null,
        private var onLongClick: ((button: Button) -> Unit)? = null,
        private var onClickPlusMinus: (() -> Unit)? = null

) :
        ListDelegationAdapter<List<Button>>(AdapterDelegatesManager<List<Button>>()) {


    init {
        delegatesManager
                .addDelegate(ButtonNumAdapterDelegate(items.size, onClick, onLongClick, onClickPlusMinus))
                .addDelegate(ButtonTextAdapterDelegate(items.size, onClick, onLongClick))
                .addDelegate(ButtonImageAdapterDelegate(items.size, onClick, onLongClick))
        setItems(items)
    }

}