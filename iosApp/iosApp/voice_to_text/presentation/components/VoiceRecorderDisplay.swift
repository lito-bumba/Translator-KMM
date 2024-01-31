//
//  VoiceRecorderDisplay.swift
//  iosApp
//
//  Created by Cazombo Bumba on 30/01/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct VoiceRecorderDisplay: View {
    var powerRatios: [Double]
    
    var body: some View {
        Canvas { context, size in
            context.clip(to: Path(CGRect(origin: .zero, size: size)))
            
            let bardWidth = 3.0
            let barCount = Int(size.width / Double(2 * bardWidth))
            let defaultLevel = 0.05
            let reversedRatios = powerRatios
                .map { ratio in
                    min(max(defaultLevel, ratio), 1.0)
                }
                .suffix(barCount)
                .reversed()
            for (i, powerRatio) in reversedRatios.enumerated() {
                let centerY = CGFloat(size.height / 2.0)
                let yTopStart = CGFloat(centerY - centerY * powerRatio)
                var path = Path()
                path.addRoundedRect(
                    in: CGRect(
                        x: CGFloat(size.width) - CGFloat(i) * 2.0 * bardWidth,
                        y: yTopStart,
                        width: bardWidth,
                        height: (centerY - yTopStart) * 2.0
                    ),
                    cornerSize: CGSize(width: 10.0, height: 10.0)
                )
                context.fill(path, with: .color(.primaryColor))
            }
        }
        .gradientSurface()
        .cornerRadius(20)
        .padding(.horizontal, 15)
        .padding(.vertical, 5)
        .shadow(radius: 4)
    }
}
