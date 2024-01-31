//
//  SmallLanguageIcon.swift
//  iosApp
//
//  Created by Cazombo Bumba on 23/12/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct SmallLanguageIcon: View {
    var language: UiLanguage
    var body: some View {
        Image(uiImage: UIImage(named: language.imageName.lowercased())!)
            .resizable()
            .frame(width: 30, height: 30)
    }
}

#Preview {
    SmallLanguageIcon(language: UiLanguage(language: .english, imageName: "english"))
}
