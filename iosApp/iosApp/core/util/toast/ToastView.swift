//
//  ToastView.swift
//  iosApp
//
//  Created by Shubham Tomar on 23/01/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation

import SwiftUI

struct ToastView: View {
  
  var style: ToastStyle
  var message: String
  var width = CGFloat.infinity
  var onCancelTapped: (() -> Void)
  
  var body: some View {
    HStack(alignment: .center, spacing: 12) {
      Image(systemName: style.iconFileName)
        .foregroundColor(style.themeColor)
      
        Text(message)
        .font(Font.caption)
        .foregroundColor(Color(uiColor: UIColor.label))
      
      Spacer(minLength: 10)
      
      Button {
        onCancelTapped()
      } label: {
        Image(systemName: "xmark")
          .foregroundColor(style.themeColor)
      }
    }
    .padding()
    .frame(minWidth: 0, maxWidth: width)
    .background(Color(uiColor : UIColor.systemBackground))
    .cornerRadius(12)
    .shadow(radius: 6)
    .padding(.horizontal, 16)
  }
}
