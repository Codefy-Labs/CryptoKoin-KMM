//
//  CrsScreen.swift
//  iosApp
//
//  Created by Shubham Tomar on 21/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct CrsScreen: View {
    @StateObject var viewModel : CrsViewModel
  
    var body: some View {
        VStack {
            TopAppBar(title: "CSR")
            ScrollView(.vertical, showsIndicators: false) {
                ExpressEntryLatestDrawView()
                Divider()
                ExpressEntryChartView(data: ExpressEntryDraw.exampleData)
                Divider()
                ExpressEntryListView(data: ExpressEntryDraw.exampleData)
            }
        }
        .task{
            viewModel.observe()
        }
    }
}
struct FitnessLevelSlider: View {
    @State private var fitnessLevel: Double = 3

    var body: some View {
        VStack {
            Text("How would you rate your fitness level?")
                .font(.title2)
                .fontWeight(.bold)
                .padding(.bottom, 20)
            
            ZStack {
                ArcSlider(value: $fitnessLevel)
                    .frame(width: 300, height: 300)
                
                Text("\(Int(fitnessLevel))")
                    .font(.system(size: 100))
                    .fontWeight(.bold)
                    .foregroundColor(.black)
                    .offset(y: 100)
                
                Text(fitnessLevelDescription)
                    .font(.title2)
                    .fontWeight(.medium)
                    .foregroundColor(.gray)
                    .offset(y: 140)
            }
        }
        .padding()
    }
    
    private var fitnessLevelDescription: String {
        switch Int(fitnessLevel) {
        case 0...1: return "Not Athletic"
        case 2...3: return "Somewhat Athletic"
        case 4...5: return "Very Athletic"
        default: return "Unknown"
        }
    }
}

struct ArcSlider: View {
    @Binding var value: Double
    
    var body: some View {
        GeometryReader { geometry in
            ZStack {
                Path { path in
                    let center = CGPoint(x: geometry.size.width / 2, y: geometry.size.height / 2)
                    path.addArc(center: center, radius: geometry.size.width / 2, startAngle: .degrees(135), endAngle: .degrees(45), clockwise: false)
                }
                .stroke(Color.gray, lineWidth: 20)
                
                Path { path in
                    let center = CGPoint(x: geometry.size.width / 2, y: geometry.size.height / 2)
                    let endAngle = 135.0 - (value * 90.0 / 5.0)
                    path.addArc(center: center, radius: geometry.size.width / 2, startAngle: .degrees(135), endAngle: .degrees(endAngle), clockwise: false)
                }
                .stroke(Color.orange, lineWidth: 20)
                
                Thumb(value: $value)
                    .offset(x: geometry.size.width / 2, y: geometry.size.height / 2)
                    .rotationEffect(.degrees(135 - (value * 90 / 5)))
            }
        }
    }
}

struct Thumb: View {
    @Binding var value: Double
    
    var body: some View {
        Circle()
            .fill(Color.orange)
            .frame(width: 40, height: 40)
            .gesture(DragGesture().onChanged { gesture in
                let newValue = Double(gesture.location.x)
                value = max(0, min(5, (newValue / 100) * 5))
            })
    }
}

struct FitnessLevelSliderPreview: PreviewProvider {
    static var previews: some View {
        FitnessLevelSlider()
    }
}
