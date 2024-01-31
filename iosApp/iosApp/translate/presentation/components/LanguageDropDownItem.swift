//
//  LanguageDropDownItem.swift
//  iosApp
//
//  Created by Cazombo Bumba on 23/12/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct LanguageDropDownItem: View {
    var language: UiLanguage
    var onClick: () -> Void
    var body: some View {
        Button(action: onClick) {
            HStack {
                if let image = UIImage(named: language.imageName.lowercased()) {
                    Image(uiImage: image)
                        .resizable()
                        .frame(width: 40, height: 40)
                        .padding(.trailing, 5)
                    Text(language.language.langName)
                        .foregroundColor(.textBlack)
                }
            }
        }
    }
}

#Preview {
    LanguageDropDownItem(
        language: UiLanguage(language: .english, imageName: "english"),
        onClick: {}
    )
}
